bits 32 ; assembling for the 32 bits architecture

;  Given the byte A and the word B, compute the doubleword C as follows:
; the bits 0-7 of C have the value 1
; the bits 8-11 of C are the same as the bits 4-7 of A
; the bits 12-19 are the same as the bits 2-9 of B
; the bits 20-23 are the same as the bits 0-3 of A
; the bits 24-31 are the same as the high byte of B


; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 10011101b
    b dw 1110010111011110b
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        
        mov ebx, 0
        mov ecx, 0
        or bx, 0000000011111111b ; bx = 0000 0000 1111 1111
        
        mov al, byte[a] ; al = 1001 1101
        and al, 11110000b
        
        mov ah, 0 ; ax = 0000 0000 1001 0000
        
        shl ax, 4 ; ax = 0000 1001 0000 0000
        
        or bx, ax ; bx = 0000 1001 1111 1111
        
        mov eax, 0
        mov ax, word[b]
        and ax, 0000001111111100b ; ax = 0000 0001 1101 1100
        shl eax, 10 ; eax = 0000 0000 0000 1101 1100 0000 0000 0000
        
        or ebx, eax ; ebx = 0000 0000 0000 1101 1101 1001 1111 1111
        
        mov eax, 0
        mov al, byte[a] ; a = 1001 1101
        and al, 00001111b ; al = 0000 1101       
        shl eax, 20
        
        or ebx, eax ; ebx = 0000 0000 1101 1101 1101 1001 1111 1111
        
        mov eax, 0
        mov ax, word[b]
        and ax, 1111111100000000b ; ax = 1110 0101 0000 0000
        shl eax, 16
        or ebx, eax
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
        
        
        
        
