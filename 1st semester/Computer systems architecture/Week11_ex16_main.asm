bits 32

; Se dau un sir care contine n reprezentari binare pe 8 biti date ca sir de caractere.
; Sa se obtina un sir care contine numerele corespunzatoare reprezentarilor binare.
;
; Exemplu:
;
; Se da: '10100111b', '01100011b', '110b', '101011b'
; Se stocheaza: 10100111b, 01100011b, 110b, 101011b


global start        

extern exit
          
import exit msvcrt.dll    

%include "numbers.asm"

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db '10100111b', '01100011b', '110b', '101011b'
    b equ $ - a
    c resb 4
    
; our code starts here
segment code use32 class=code
    start:  
    
        mov edi, a ; we move in EDI the starting address of a
        mov esi, a ; we move in ESI the starting address of a
        mov ecx, b ; we move in ECX the length of the string a
        mov eax, 0 ; we move in eax 0 in order to store the results in [c+0], [c+1], [c+2]
        
        ComputeNumbers:
            
            push eax ; we push EAX on the stack
            push ecx ; we push ECX on the stack
            
            mov ecx, 8 ; we move in ECX the longest possible input of a byte (8 bits)
                
            find_esi:
            
                cmp byte[edi], 'b' ; we compare the current element of the string with 'b'
                je FindEdi ; if we found 'b' we are at the end of the number
                
                inc edi ; we increase EDI in case we did not find 'b'
                jmp Ok 
                    
                FindEdi:
                    mov ecx, 1 ; if we found 'b' we close the loop
                    
                Ok:
                    
            loop find_esi ; we loop the insturctions untill we find the end of the 1st number of the given string
            
            push esi ; we push ESI on the stack
            push edi ; we push EDI on the stack
            
            mov ecx, edi
            sub ecx, esi ; we store in ECX the length of the number (ex: 1000_1111 -> ECX = 8)
            mov ebx, 0
            mov edx, 0
            mov eax, 0
            dec edi ; we decrease EDI by one, because 'b' is not a character of the binary number -> we do not need it in our representation
            mov bl, 1
            
            call numbers ; we call the ASM file 'numbers' to compute the number (from base 2 -> base 10)
            
            pop edi ; we pop EDI from the stack
            pop esi ; we pop ESI from the stack
            pop ecx ; we pop ECX from the stack
            pop eax ; we pop EAX from the stack
            
            mov ebx, edi 
            sub ebx, esi
            
            inc edi ; we increment EDI to pass the string element 'b' -> EDI is now at the beginning of the new number
            
            sub ecx, ebx ; we subtract from ecx the length of the number
            mov esi, edi ; we move in ESI the beginning address of the next binary number
            mov [c + eax], dl ; we save the decimal form of the number in memory
            inc eax ; we increase the counter of the decimal numbers (if we saved a number in [c + 0] we need [c + 1] for the next number
            
        loop ComputeNumbers
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program

        