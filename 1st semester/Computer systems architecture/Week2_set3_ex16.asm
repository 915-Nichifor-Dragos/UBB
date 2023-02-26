bits 32 ; assembling for the 32 bits architecture

;(a+b)/2 + (10-a/c)+b/4         a,b,c - byte, d - word

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 8
    b db 5
    c db 4

; our code starts here
segment code use32 class=code
    start:
        
        mov al, byte[a]
        add al, byte[b]
        mov ah, 0
        mov dl, 2
        div dl
        
        mov bl, al ; bl =(a+b)/2
        mov al, byte[a]
        mov ah, 0
        mov dl, byte[c]
        div dl
        mov ah, 10
        sub ah, al
        add bl, ah ; bl = (a+b)/2 + (10-a/c)
        
        mov al, byte[b]
        mov ah, 0
        mov cl, 4
        div cl
        add bl, al ; bl = (a+b)/2 + (10-a/c) + b/4
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
