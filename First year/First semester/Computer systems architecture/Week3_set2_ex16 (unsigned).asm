bits 32 ; assembling for the 32 bits architecture

;x/2+100*(a+b)-3/(c+d)+e*e;             a,c-word; b,d-byte; e-doubleword; x-qword

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 3
    c dw 1
    b db 2
    d db 2
    e dw 10
    x dq 20

; our code starts here
segment code use32 class=code
    start:
    
        mov eax, dword[x]
        mov edx, dword[x + 4]
        mov ebx, 2
        div ebx
        mov ebx, eax ; ebx = x / 2
        
        mov al, byte[b]
        mov ah, 0
        add ax, word[a] ; ax = a + b
        mov dx, 100
        mul dx
        push dx
        push ax
        pop ecx ; ecx = 100 * (a + b)
        add ebx, ecx ; ebx = x / 2 + 100 * (a + b)
        
        mov al, byte[d]
        mov ah, 0
        add ax, word[c]
        mov cx, ax
        mov eax, 3
        div ax
        mov cx, ax
        mov eax, 0
        mov ax, cx
        sub ebx, eax ;ebx = x / 2 + 100 * (a + b) - 3 / (c + d)
        
        mov ax, word[e]
        mov dx, word[e]
        mul dx
        push dx
        push ax
        pop ecx
        add ebx, ecx 
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program