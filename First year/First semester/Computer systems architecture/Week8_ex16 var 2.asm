bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'a', 'c', 'd', 'e'
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
        
        
        mov ecx, l1
        add ecx, l2 ; we store the sum of l1 and l2 in ecx
        
        mov ebx, 0 ; the index of s3
        mov esi, 0 ; the index of s1
        mov edi, 0 ; the index of s2
        
        .my_loop:
        
            cmp esi, l1
            je .Done ; if we finished all the elements from the 1st string jump to .Done
            cmp edi, l2
            je .Done2 ; if we finished all the elements from the 2nd string jump to .Done2
            
            mov al, [s1 + esi]
            cmp al, [s2 + edi] ; compare the letters from the current index in s1 and s2 (s1[esi], s2[edi])
            
            jae .move_second ; if s1[esi] > s2[edi], move the second letter, if not move the 1st letter
                
            .move_first: ; move the 1st letter available from s1 to s3
                mov al, [s1 + esi]
                inc esi
                mov [s3 + ebx], al
                inc ebx
                jmp .done_loop
            
            .move_second: ; move the 1st letter available from s2 to s3
                mov al, [s2 + edi]
                inc edi
                mov [s3 + ebx], al
                inc ebx
                jmp .done_loop
                
            .done_loop:
            
        loop .my_loop
            
            .Done: ; in case we finished all the elements from the 1st string we check if there are any elements left in the 2nd string
                mov ecx, l2
                sub ecx, edi
                
                .loop_EDI:
                    mov al, [s2 + edi]
                    mov [s3 + ebx], al
                    inc edi
                    inc ebx
                    
                loop .loop_EDI
                
                jmp .finish
            
            .Done2: ; in case we finished all the elements from the 2nd string we check if there are any elements left in the 1st string
                mov ecx, l1
                sub ecx, esi
                
                .loop_ESI:
                    mov al, [s2 + esi]
                    mov [s3 + esi], al
                    inc esi
                    inc ebx
                
                loop .loop_ESI
                
                jmp .finish
                
        .finish:
            push    dword 0      ; push the parameter for exit onto the stack
            call    [exit]       ; call exit to terminate the program