bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a db 10
    b db 12
    c db 63
    d db 4

; our code starts here
segment code use32 class=code
    start:
    
        mov AL, [a]
        add AL, 13
        sub AL, [c]
        add AL, [d]
        sub AL, 7
        add AL, [b]
        
        
        
        
        
