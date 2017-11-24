
JMP	START
	DB	99		; Memory location for hardware timer interupt, returns from interupt
	DB	99		; This interupt is not used, if it is used by manually viewing the keyboard, returns from interupt
	DB	9A		; Memory location for keypad interupts
	DB	"BOTTOMUP"	; Add the string sequence to memory so the printing could be done with a loop
	DB	00		; putting null after UP takes less memory than clearing the output manually before printing
	DB	00
	DB	00
	DB	00
	DB	"TOPDOWN"

Start:
	CLO			; Close windows
	OUT	06		; Display Lift
	OUT	08		; Display Keypad
	IN 	06		; Get input from Lift
	AND	AL, 08		; is lift at the bottom?
	JZ	PrintTop	; if not at the bottom, start from top
PrintBottom:
	MOV 	BL, 05		; BL points to B
	MOV	CL, 0B		; CL points to next letter after the last letter
	CALL	80		; Print "BOTTOM"
Bottom:
	IN	06		; Get current state of the lift
	AND	AL, 20		; Check if "Up" button is pressed
	JZ	Bottom		; If not pressed check again
Up:
	MOV	BL, 0B		; BL point to U
	MOV	CL, 11		; BL points to next memory location after the last letter
	CALL 	80		; Print "UP"
	IN	06		; Get current state of the lift
	OR	AL, 01		; Enable "Up" bit
	OUT	06		; Lift is going up
	STI			; Start handling interupts
checkTOP:
	AND	AL, 04		; Is lift at the top?
	IN	06		; Get input for next possible loop
	JZ	checkTOP	; If not at top repeat check
	
	SUB	AL, 21		; Subtract "Up" button bit and bit that drives lift up from current lift value
	OUT	06		; Stop the lift clear "Up" button
PrintTop:
	MOV	BL, 11		; BL points to T
	MOV	CL, 14		; CL point next memory location after last letter
	CALL	80		; Print "TOP"
Top:
	IN	06		; Get current status of the lift
	AND	AL, 10		; is "Down" bit enabled?
	JZ	Top		; if not enabled print and check
Down:
	CLI			; No need to handle interupts while lift is already going down and when the lift is at the bottom
	MOV	BL, 14		; BL point to D
	MOV	CL, 18		; CL points to next memory location after last letter
	CALL	80		; Print "DOWN"
	OR	AL, 02		; Enable "Down" bit
	OUT	06		; Lift goes down
checkBottom:
	AND	AL, 08		; Is lift at the bottom?
	IN	06		; Get input for next possible loop
	JZ	checkBottom	; If not at bottom repeat check
	
	SUB	AL, 12		; Subtract "Down" button bit and bit that drives lift down from current lift value
	OUT	06		; Stop the lift cleat "Down" button
	JMP	PrintBottom	; Lift is now at the bottom

;=========================================================
; This is subroutine for printing, BL points to the start of the word, CL points to one memory location further than the last letter of the word
	ORG	80
	PUSHF			; push flag onto stack
	PUSH	AL		; Save AL value
	MOV	DL, C0		; DL points to the first memory location needed to start printing
Print:
	MOV	AL, [BL]	; MOVE B to AL
	MOV	[DL], AL	; MOVE B to C0
	INC	BL		; BL points to the next letter
	INC	DL		; DL points to next memory location for printing
	CMP	BL, CL		; CMP current index with target index
	JNZ	Print		; if They are not equal continue printing
	POP	AL		; Get AL from stack
	POPF			; Get flag from stack
	RET
;=========================================================
	ORG	99		; this subroutine only returns, used for hardware and keyboard interupts
Return:
	IRET
;=========================================================
	ORG	9A
	CLI			; Don't handle any more interupts while handling interupt
	IN	06		; Get lift status
	PUSH	AL		; Save status onto stack
	IN	08		; Get input from keypad device
	CMP	AL, 0D		; Is input equal to Enter key?
	POP	AL		; Put original lift status to AL regardless
	STI			; Safe to assume that interupts need to be handled again since interupt was called in the first place
	JNZ	Return		; if not enter, return from interupt

	CLI			; If Enter stop handling interupts again 
	AND	AL, 30		; Preserve up and down button states
	OR	AL, 10		; Enable Down button
	OUT	06		; Stop the lift so it doesn't go up while "DOWN" is printing
	POP	DL		; POP instruction pointer value to DL (so stack doesn't expand each time interupt is called)
	JMP	Down		; Make lift go down
;=========================================================
END