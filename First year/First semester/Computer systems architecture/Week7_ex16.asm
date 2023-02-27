bits 32 ; assembling for the 32 bits architecture

; Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D prin concatenarea elementelor de pe pozitiile impare din S2 cu elementele de pe pozitiile pare din S1.

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    S1 db 'a', 'b', 'c', 'b', 'e', 'f'
    l1 equ $ - S1
    S2 db '1', '2', '3', '4', '5'
    l2 equ $ - S2
    D times (l1 / 2) + (l2 / 2) resb 0
    

; our code starts here
segment code use32 class=code
    start:
        mov ecx, l2 ; we move the length of the 2nd string in ECX
        mov esi, 0 ; in esi we save the current position in S2
        mov edi, 0 ; in edi we save the current position in D (edi will increase each time we add a element during the loops)
        
        Repeat:
            cmp ecx, 0
            dec ecx ; we decrease ecx because we will increase esi 2 times (the loop only decreases ecx once)
            jl Sfarsit ; if ecx is less than 0, the program stops
            mov al, [S2 + esi]
            mov [D + edi], al ; we move the elements found on even positions in S2 to D
            inc esi ; we increase esi 2 times in order to jump from an even position to an even one (ex: jump from 1 to 3, we do not need 2)
            inc esi
            inc edi ; increase the edi, after adding a new character to D
        loop Repeat
        
        Sfarsit:
        
            mov ecx, l1 ; we move the length of the 1st string in ECX
            mov esi, 0 ; in esi we save the current position in S1
            
            Repeat2:
                cmp ecx, 0
                inc esi ; we increse ecx in order to copy the elements on odd positions (we do not need the 1st element of the string, we need the 2nd one)
                dec ecx ; we decrease ecx because we will increase esi 2 times (the loop only decreases ecx once)
                jl Sfarsit2 ; if ecx is less than 0, the program stops
                mov al, [S1 + esi]
                mov [D + edi], al ; we move the elements found on odd positions in S1 to D
                inc esi 
                inc edi ; increase the edi, after adding a new character to D
            loop Repeat2
            
            Sfarsit2:
                
                push    dword 0      ; push the parameter for exit onto the stack
                call    [exit]       ; call exit to terminate the program