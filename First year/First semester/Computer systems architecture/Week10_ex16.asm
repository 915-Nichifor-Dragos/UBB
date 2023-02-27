bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program

extern exit, printf, scanf            
import exit msvcrt.dll     
import printf msvcrt.dll     ; indicating to the assembler that the printf fct can be found in the msvcrt.dll library
import scanf msvcrt.dll 
                          
; our data is declared here (the variables needed by our program)
segment data use32 class=data

    message_first db "a=", 0
    message_second db "b=", 0
    message_finish db "n=%x", 0
    format db "%d", 0
    a dw 0
    b dw 0
    
; Sa se citeasca de la tastatura doua numere a si b (in baza 10). Sa se calculeze si sa se afiseze media lor aritmetica in baza 16.
; our code starts here
segment code use32 class=code
    start:
    
        push dword message_first
        call [printf]
        add esp, 4 * 1
        push dword a
        push dword format
        call[scanf]
        add esp, 4 * 2 ; In this section we read the 1st number (a)
        
        push dword message_second
        call[printf]
        add esp, 4 * 1        
        push dword b
        push dword format
        call[scanf]
        add esp, 4 * 2 ; In this section we read the 2nd number (b)
    
        mov eax, 0
        add ax, [a]
        add ax, [b] ; we add the numbers in the AX register
        mov bl, 2 
        div bl ; we divide the sum by 2 in order to compute the arithmetic average
        mov bl, al ; we put the arithmetic average in BL so we can now clean EAX
        mov eax, 0 ; we clean EAX
        mov al, bl ; we move the arithmetic average back in the AL register in order to print it
        
        push eax
        push dword message_finish
        call [printf]
        add esp, 4 * 2 ; In this section we print the arithmetic average of a and b
        
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
