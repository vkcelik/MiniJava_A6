.ORIG x3000
  ;  initialize CONST0 and CONST1
  AND R2, R2, 0
  ADD R3, R2, 1
  ;  set SFP and HP 
  LD R6, LAB_12
  LD R4, LAB_13
  BR LAB_11
  ;  data for SFP and HP
LAB_12
.FILL 32768
LAB_13
.FILL 65023
  ;  arguments to main
LAB_9
.FILL 0
LAB_11
LAB_10
  ; 
  ;  METHOD main
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  LEA R0, LAB_9
  STR R0, R6, 3
  ADD R5, R6, 7
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  INT CONST 5
  ; 
  LD R0, 3
  ADD R5, R5, 1
  STR R0, R5, -1
  BR LAB_14
  ;  value 
.FILL 5
LAB_14
  ; 
  ;  INT CONST END
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER x lhs 
  ; 
  ;  push address 
  ADD R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  store 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  STR R0, R1, 0
  ; 
  ;  ASSIGN END
  ; 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  INT CONST 3
  ; 
  LD R0, 3
  ADD R5, R5, 1
  STR R0, R5, -1
  BR LAB_15
  ;  value 
.FILL 3
LAB_15
  ; 
  ;  INT CONST END
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER y lhs 
  ; 
  ;  push address 
  ADD R0, R6, 5
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  store 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  STR R0, R1, 0
  ; 
  ;  ASSIGN END
  ; 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  MULT BEGIN 
  ; 
  ;  ADD lhs 
  ; 
  ;  IDENTIFIER x rhs 
  ; 
  ;  push value 
  LDR R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  ADD rhs 
  ; 
  ;  IDENTIFIER y rhs 
  ; 
  ;  push value 
  LDR R0, R6, 5
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ;  sum = 0 
  AND R7, R7, 0
  ;  add label for loop 
LAB_16
  ;  sum = sum + multiplikator 
  ADD R7, R7, R0
  ;  multiplikand = multiplikand + (-1) 
  ADD R1, R1, -1
  ;  jump to loop while multiplikand > 0 
  BRp LAB_16
  ;  push result on stack 
  ADD R5, R5, 1
  STR R7, R5, -1
  ; 
  ;  MULT END 
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER result lhs 
  ; 
  ;  push address 
  ADD R0, R6, 6
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  store 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  STR R0, R1, 0
  ; 
  ;  ASSIGN END
  ; 
  ; 
  ;  RETURN BEGIN
  ; 
  ;  get return PC from stack frame
  LDR R7, R6, 2
  RET
  ; 
  ;  RETURN END
  ; 
  ; 
  ;  METHOD END main
  ; 
  ; 
  ;  helper functions 
  ; 
  ; 
  ; 
  ; 
  ; 
  ;  translate top of stack to string, pushes result
  ; 
LAB_3
  ; 
  ; This algorithm takes the 2's complement representation of a signed
  ; integer, within the range -999 to +999, and converts it into an ASCII
  ; string consisting of a sign digit, followed by three decimal digits.
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  ADD R5, R5, 1
  STR R7, R5, -1
  LEA R1, LAB_17
  ADD R0, R0, 0
  BRzp LAB_20
  LD R7, LAB_18
  STR R7, R1, 0
  ADD R1, R1, 1
  NOT R0, R0
  ADD R0, R0, 1
LAB_20
  AND R7, R7, 0
  LD R2, LAB_21
LAB_22
  ADD R0, R0, R2
  BRn LAB_23
  ADD R7, R7, 1
  BR LAB_22
  ; 
LAB_23
  ADD R7, R7, 0
  BRz LAB_47
  LD R2, LAB_19
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_47
  LD R2, LAB_24
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_25
  LD R2, LAB_26
LAB_27
  ADD R0, R0, R2
  BRn LAB_28
  ADD R7, R7, 1
  BR LAB_27
  ; 
LAB_28
  ADD R7, R7, 0
  BRz LAB_46
  LD R2, LAB_19
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_46
  LD R2, LAB_29
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_30
  LD R2, LAB_31
LAB_32
  ADD R0, R0, R2
  BRn LAB_33
  ADD R7, R7, 1
  BR LAB_32
  ; 
LAB_33
  ADD R7, R7, 0
  BRz LAB_45
  LD R2, LAB_19
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_45
  LD R2, LAB_34
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_35
  LD R2, LAB_36
LAB_37
  ADD R0, R0, R2
  BRn LAB_38
  ADD R7, R7, 1
  BR LAB_37
  ; 
LAB_38
  ADD R7, R7, 0
  BRz LAB_44
  LD R2, LAB_19
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_44
  ADD R0, R0, 10
  ; 
  LD R7, LAB_19
LAB_40
  ADD R7, R7, R0
  STR R7, R1, 0
  ADD R1, R1, 1
  AND R2, R2, 0
  STR R2, R1, 0
  ADD R5, R5, -1
  LDR R7, R5, 0
  LEA R0, LAB_17
  ADD R5, R5, 1
  STR R0, R5, -1
  RET
  ; 
  ; data
  ; 
LAB_17
  .BLKW 7
LAB_18
.FILL 45
LAB_19
.FILL 48
LAB_21
.FILL -10000
LAB_26
.FILL -1000
LAB_31
.FILL -100
LAB_36
.FILL -10
LAB_41
.FILL -1
LAB_24
.FILL 10000
LAB_29
.FILL 1000
LAB_34
.FILL 100
LAB_39
.FILL 10
LAB_42
.FILL 1
  ; 
  ;  print newline 
  ; 
LAB_1
  ADD R5, R5, 1
  STR R7, R5, -1
  LD R0, 1
  BR LAB_48
.FILL LAB_49
LAB_49
.STRINGZ "\n"
LAB_48
  TRAP x22
  ADD R5, R5, -1
  LDR R7, R5, 0
  RET
LAB_2
  ; 
  ;  create an object with size top of stack, result in HP
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  ; allocate object
  NOT R0, R0
  ADD R0, R0, 1
  ADD R4, R4, R0
  ADD R5, R5, 0
  BRp LAB_52
  ADD R4, R4, 0
  BRp LAB_53
LAB_54
  ADD R1, R5, 0
  NOT R1, R1
  ADD R1, R1, 1
  ADD R1, R4, R1
  BRp LAB_51
  BR LAB_53
LAB_52
  ADD R4, R4, 0
  BRn LAB_51
  BR LAB_54
LAB_53
  TRAP x25
LAB_51
  ADD R5, R5, 1
  STR R4, R5, -1
  RET
  ; 
  ;  nullify 
  ; 
  ;  overwrites memory area a to b with 0s 
  ;  expects operands in top of stack 
  ;  assumes a<b!!! 
LAB_5
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ADD R5, R5, 1
  STR R0, R5, -1
  NOT R0, R0
  ADD R0, R0, 1
  ADD R1, R1, R0
  ADD R5, R5, -1
  LDR R0, R5, 0
LAB_55
  STR R2, R0, 0
  ADD R0, R0, 1
  ADD R1, R1, -1
  BRp LAB_55
  RET
  ; 
  ;  multiplication routine 
  ; 
  ;  expects operands on top of stack 
LAB_4
  ;  while loop that multiplies a and b, R7 is sum 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ;  get a and b
  ;  check signs 
  ;  CONST0 is used to store the flag of the result
  ;  0 means positive (default), 1 negative
  ADD R0, R0, 0
  ;  if one is zero we're done
  BRz LAB_58
  ;  if a is positive, jump
  BRp LAB_59
  ;  negate a 
  NOT R0, R0
  ADD R0, R0, 1
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_58
  ;  if b is positive, jump
  BRp LAB_60
  ;  a is neg, b is too
  ;  negate b
LAB_61
  NOT R1, R1
  ADD R1, R1, 1
  ;  go, multiply!
  BR LAB_56
LAB_60
  ;  a is negative, b is positive
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  ;  go, multiply!
  BR LAB_56
LAB_59
  ;  a is positive 
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_58
  ;  if b is positive, go multiply!
  BRp LAB_56
  ;  a is pos, b is neg
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  BR LAB_61
  ; 
  ;  multiply 
  ; 
LAB_56
  ADD R5, R5, 1
  STR R7, R5, -1
  ;  reset sum
  AND R7, R7, 0
LAB_62
  ADD R7, R7, R0
  ADD R1, R1, -1
  BRp LAB_62
  ;  adjust sign 
  ADD R2, R2, 0
  BRz LAB_63
  NOT R7, R7
  ADD R7, R7, 1
LAB_63
  ;  reset CONST0 
  AND R2, R2, 0
  ;  transfer sum to TMP0, get RET from stack
  ADD R0, R7, 0
  ADD R5, R5, -1
  LDR R7, R5, 0
  ;  result in R0 
  ADD R5, R5, 1
  STR R0, R5, -1
  RET
  ;  one factor was 0
LAB_58
  ADD R5, R5, 1
  STR R2, R5, -1
  RET
  ; 
  ;  null pointer exception 
  ; 
  ;  prints error message and exits
LAB_7
  LEA R0, LAB_64
  TRAP x22
  TRAP x25
LAB_64
.STRINGZ "Null pointer exception
"
  ; 
  ;  index out of bounds exception 
  ; 
  ;  prints error message and exits
LAB_8
  LEA R0, LAB_65
  TRAP x22
  TRAP x25
LAB_65
.STRINGZ "Index out of bounds exception
"
  ; 
  ;  add two strings 
  ; 
  ;  expects args on top of stack, puts result on stack
LAB_6
  LDR R0, R5, -2
  BRnp LAB_69
  LEA R0, LAB_71
  STR R0, R5, -2
  BR LAB_70
LAB_69
  LDR R0, R5, -1
  BRnp LAB_70
  LEA R0, LAB_71
  STR R0, R5, -1
  BR LAB_70
LAB_71
.FILL LAB_72
.FILL 5
LAB_72
.STRINGZ "null"
LAB_70
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ADD R5, R5, 1
  STR R7, R5, -1
  ;  compute combined length 
  LDR R7, R0, 1
  ADD R5, R5, 1
  STR R0, R5, -1
  ADD R0, R7, 0
  LDR R7, R1, 1
  ADD R5, R5, 1
  STR R1, R5, -1
  ADD R0, R0, R7
  BR LAB_67
LAB_68
.FILL LAB_2
LAB_66
.FILL 2
  ;  copy string 
LAB_74
.FILL LAB_73
LAB_73
  ADD R5, R5, 1
  STR R7, R5, -1
  LDR R0, R0, 0
LAB_76
  LDR R7, R0, 0
  BRz LAB_75
  STR R7, R1, 0
  ADD R0, R0, 1
  ADD R1, R1, 1
  BR LAB_76
LAB_75
  ADD R5, R5, -1
  LDR R7, R5, 0
  RET
LAB_67
  ;  invoke new method 
  LD R1, LAB_66
  ADD R0, R1, R0
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_68
  JSRR R1
  ;  initialize object pointed to by HP 
  ;  initialize string 
  ADD R0, R4, 2
  STR R0, R4, 0
  ;  initialize length 
  ADD R5, R5, -1
  LDR R0, R5, 0
  STR R0, R4, 1
  ;  get two strings 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ADD R5, R5, 1
  STR R1, R5, -1
  ;  copy first string from TMP0->0 to HP->0 
  LDR R1, R4, 0
  LD R7, LAB_74
  JSRR R7
  ;  copy second string from TMP0->0 to HP->0 
  ADD R5, R5, -1
  LDR R0, R5, 0
  LD R7, LAB_74
  JSRR R7
  ADD R5, R5, -1
  LDR R7, R5, 0
  ADD R5, R5, 1
  STR R4, R5, -1
  RET
.END
