bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)

;Se dau doua siruri de caractere ordonate alfabetic s1 si s2. Sa se construiasca prin interclasare sirul ordonat s3 care sa contina toate elementele din s1 si s2.

segment data use32 class=data
    s1 db 'a', 'c', 'd', 'e', 'h', 'u'
    l1 equ $ - s1
    s2 db 'b', 'f', 'g', 'z'
    l2 equ $ - s2
    
    s3 resb l1 + l2

; our code starts here
segment code use32 class=code
    start:
        
        ; k = 0
        ; c1 = 0, c2 = 0
        ; while (while c1 < l1 and c2 < l2) {
        ;   if s1[c1] > s2[c2]
        ;       s3[k++] = s2[c2]
        ;           c2 ++;
        ;   else
        ;       s3[k++] = s1[c1]
        ;           c1 ++;
        ; while (c1 < l1) {
        ;   s3[k++] = s1[c1++]
        ;
        ; while (c2 < l2)
        ;   s3[k++] = s2[c2++]
        
        mov ebx, 0
        mov esi, s1 ; we store the address of s1 in esi
        mov edi, s2 ; we store the address of s2 in edi
        
        mov ecx, l1 
        add ecx, l2 ; we store the sum of l1 and l2 in ecx for the loop
        cld ; we clear direction flag
        
        .my_loop:
        
            mov eax, esi ; we check if there is any element left in s1, if not we jump to .Done
            sub eax, s1
            cmp eax, l1
            je .Done
            
            mov eax, edi ; we check if there is any element left in s2, if not we jump to .Done2
            sub eax, s2
            cmp eax, l2
            je .Done2
            
            cmpsb ; we compare the current element of the 2 strings (s1[esi] and s2[edi])
            
            jae .move_second ; if the 1st element is above the second element, we move the 2nd element in the destination string
            
            .move_first: ; the label that is responsible with moving the element from the 1st string to the destination string
                
                dec esi
                dec edi
                
                lodsb ; we store the byte from esi in al
                
                dec esi
                
                mov [s3 + ebx], al ; we move the byte from al to [s3 + ebx]
                
                inc ebx
                inc esi
                
                jmp .done_loop ; jumps at the end of the loop
            
            .move_second: ; the label that is responsible with moving the element from the 2nd string to the destination string
                
                dec esi
                dec edi
                
                mov edx, esi ; we swap edi with esi in order to be able to use "lodsb" function
                mov esi, edi
                mov edi, edx
                
                lodsb ; we store the byte from esi in al
                
                dec esi
                
                mov [s3 + ebx], al ; we move the byte from al to [s3 + ebx]
                
                mov edx, esi
                mov esi, edi
                mov edi, edx
                
                inc ebx
                inc edi
                
            .done_loop:
            
        loop .my_loop
        
        .Done: ; in case we finished all the elements from the 1st string we check if there are any elements left in the 2nd strin
        
            mov eax, edi ; we swap edi with esi in order to be able to use "lodsb" function
            sub eax, s2
            sub eax, l2
            neg eax
            
            mov ecx, eax
            
            .Loop_EDI:
            
                mov edx, esi
                mov esi, edi
                mov edi, edx
                
                lodsb ; we store the byte from esi in al
                
                dec esi
                
                mov [s3 + ebx], al ; we move the byte from al to [s3 + ebx]
                
                mov edx, esi
                mov esi, edi
                mov edi, edx
                
                inc ebx
                inc edi
            
            loop .Loop_EDI
            
            jmp .End
            
        .Done2: ; in case we finished all the elements from the 2nd string we check if there are any elements left in the 1st string
        
            mov eax, esi
            sub eax, s1
            sub eax, l1
            neg eax
            
            mov ecx, eax
            
            .Loop_ESI
            
                lodsb ; we store the byte from esi in al
                
                dec esi
                
                mov [s3 + ebx], al ; we move the byte from al to [s3 + ebx]
                
                inc ebx
                inc esi
                
            loop .Loop_ESI
            
            jmp .End
            
        .End:
            push    dword 0      ; push the parameter for exit onto the stack
            call    [exit]       ; call exit to terminate the program