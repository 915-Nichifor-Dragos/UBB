bits 32 ; assembling for the 32 bits architecture


; A text file is given. Read the content of the file, count the number of letters 'y' and 'z' and display the values on the screen. The file name is defined in the data segment.


; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fopen, fread, fclose, fprintf       
import exit msvcrt.dll    
import fopen msvcrt.dll
import fclose msvcrt.dll
import fread msvcrt.dll
import fprintf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    file_name db "file_txt.txt", 0
    file_output db "output_txt.txt", 0
    output db "The number of y is %d and the number of z is %d", 0
    access_mode db "r", 0
    output_mode db "w", 0
    a dd 0
    b dd 0
    y db 'y'
    z db 'z'
    len equ 100
    descriptor dd -1
    descriptor_output dd -1
    text times 100 db 0
    
; our code starts here
segment code use32 class=code
    start:
    
        push access_mode
        push file_name
        call [fopen]
        add esp, 4 * 2
        
        cmp eax, 0
        je finish
        
        mov [descriptor], eax
        
        push dword [descriptor]
        push dword len
        push dword 1
        push dword text
        call [fread]
        add esp, 4 * 4
        
        push dword [descriptor]
        call [fclose]
        add esp, 4
        
        mov ecx, len
        mov esi, 0
        mov edx, 0
        mov ebx, 0
        
        my_loop:
            mov al, [text + esi]
            cmp al, byte[y]
            je eq_y     
            cmp al, byte[z]
            je eq_z
            jmp done
            
            eq_z:
                inc ebx
                jmp done
             
            eq_y:
                inc edx
                jmp done
            
            done:
                inc esi
        
        
        loop my_loop
        
        mov dword[a], ebx
        mov dword[b], edx
        
        push output_mode
        push file_output
        call [fopen]
        add esp, 4 * 2
        
        cmp eax, 0
        je finish
        
        mov [descriptor_output], eax
        
        push dword [a]
        push dword [b]
        push dword output
        push dword [descriptor_output]
        call [fprintf]
        add esp, 4 * 4
        
        push dword [descriptor_output]
        call [fclose]
        add esp, 4
        
        finish:
        
            push    dword 0      ; push the parameter for exit onto the stack
            call    [exit]       ; call exit to terminate the program
        
        
