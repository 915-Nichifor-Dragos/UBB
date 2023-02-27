bits 32 ; assembling for the 32 bits architecture
; (d-a)-(a-c)-d    a - byte, b - word, c - double word, d - qword 
; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 7
    b dw 10
    c dd 21
    d dq 213

; our code starts here
segment code use32 class=code
    start:
        mov ebx, 0
        mov edx, 0
        mov eax, 0
        mov ecx, 0
        mov al, byte[a]
        cbw
        cwde
        cdq
        mov ebx, dword[d]
        mov ecx, dword[d + 4]
        sub ecx, edx
        sbb ebx, eax      ; ebx:ecx = d - a
        
        mov eax, 0
        mov al, byte[a]
        cbw
        cwde
        sub eax, dword[c] ; eax = a - c
        
        cdq               ; eax:edx =a - c
        clc
        sub ebx, eax
        sbb ecx, edx 
        
        sub ebx, dword[d]
        sbb ecx, dword[d + 4]
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
        
        
       
        
        
        