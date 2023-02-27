bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 4
    b dw 5
    c db 2
    
    d db 2
    e db 4
    
; our code starts here
segment code use32 class=code
    start:
        
        mov AX, [a]
        add AX, [b]
        div BYTE[d]
        mov DL, AL
        
        mov AX, [a]
        div BYTE[c]
        mov AH, 10
        sub AH, AL
        mov DH, AH
        
        mov AX, [b]
        div BYTE[e]
        
        mov AH, AL
        add AH, DL
        add AH, DH
        
        
        
        
        
        
        
        
        
        
        
