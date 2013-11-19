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
  ;  string contents
LAB_14
.STRINGZ "Hello"
LAB_15
.STRINGZ "Christian"
  ;  strings
LAB_16
.FILL LAB_14
.FILL 6
LAB_17
.FILL LAB_15
.FILL 10
  ;  args array
LAB_9
.FILL 2
.FILL LAB_16
.FILL LAB_17
LAB_11
LAB_10
  ; 
  ;  METHOD main
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  LEA R0, LAB_9
  STR R0, R6, 3
  ADD R5, R6, 5
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  INT CONST 0
  ; 
  ADD R5, R5, 1
  STR R2, R5, -1
  ;  lhs 
  ; 
  ;  IDENTIFIER i lhs 
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
  ;  WHILE BEGIN 
  ; 
  ; 
  ;  WHILE END
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
  LEA R1, LAB_18
  ADD R0, R0, 0
  BRzp LAB_21
  LD R7, LAB_19
  STR R7, R1, 0
  ADD R1, R1, 1
  NOT R0, R0
  ADD R0, R0, 1
LAB_21
  AND R7, R7, 0
  LD R2, LAB_22
LAB_23
  ADD R0, R0, R2
  BRn LAB_24
  ADD R7, R7, 1
  BR LAB_23
  ; 
LAB_24
  ADD R7, R7, 0
  BRz LAB_48
  LD R2, LAB_20
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_48
  LD R2, LAB_25
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_26
  LD R2, LAB_27
LAB_28
  ADD R0, R0, R2
  BRn LAB_29
  ADD R7, R7, 1
  BR LAB_28
  ; 
LAB_29
  ADD R7, R7, 0
  BRz LAB_47
  LD R2, LAB_20
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_47
  LD R2, LAB_30
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_31
  LD R2, LAB_32
LAB_33
  ADD R0, R0, R2
  BRn LAB_34
  ADD R7, R7, 1
  BR LAB_33
  ; 
LAB_34
  ADD R7, R7, 0
  BRz LAB_46
  LD R2, LAB_20
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_46
  LD R2, LAB_35
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_36
  LD R2, LAB_37
LAB_38
  ADD R0, R0, R2
  BRn LAB_39
  ADD R7, R7, 1
  BR LAB_38
  ; 
LAB_39
  ADD R7, R7, 0
  BRz LAB_45
  LD R2, LAB_20
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_45
  ADD R0, R0, 10
  ; 
  LD R7, LAB_20
LAB_41
  ADD R7, R7, R0
  STR R7, R1, 0
  ADD R1, R1, 1
  AND R2, R2, 0
  STR R2, R1, 0
  ADD R5, R5, -1
  LDR R7, R5, 0
  LEA R0, LAB_18
  ADD R5, R5, 1
  STR R0, R5, -1
  RET
  ; 
  ; data
  ; 
LAB_18
  .BLKW 7
LAB_19
.FILL 45
LAB_20
.FILL 48
LAB_22
.FILL -10000
LAB_27
.FILL -1000
LAB_32
.FILL -100
LAB_37
.FILL -10
LAB_42
.FILL -1
LAB_25
.FILL 10000
LAB_30
.FILL 1000
LAB_35
.FILL 100
LAB_40
.FILL 10
LAB_43
.FILL 1
  ; 
  ;  print newline 
  ; 
LAB_1
  ADD R5, R5, 1
  STR R7, R5, -1
  LD R0, 1
  BR LAB_49
.FILL LAB_50
LAB_50
.STRINGZ "\n"
LAB_49
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
  BRp LAB_53
  ADD R4, R4, 0
  BRp LAB_54
LAB_55
  ADD R1, R5, 0
  NOT R1, R1
  ADD R1, R1, 1
  ADD R1, R4, R1
  BRp LAB_52
  BR LAB_54
LAB_53
  ADD R4, R4, 0
  BRn LAB_52
  BR LAB_55
LAB_54
  TRAP x25
LAB_52
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
LAB_56
  STR R2, R0, 0
  ADD R0, R0, 1
  ADD R1, R1, -1
  BRp LAB_56
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
  BRz LAB_59
  ;  if a is positive, jump
  BRp LAB_60
  ;  negate a 
  NOT R0, R0
  ADD R0, R0, 1
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_59
  ;  if b is positive, jump
  BRp LAB_61
  ;  a is neg, b is too
  ;  negate b
LAB_62
  NOT R1, R1
  ADD R1, R1, 1
  ;  go, multiply!
  BR LAB_57
LAB_61
  ;  a is negative, b is positive
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  ;  go, multiply!
  BR LAB_57
LAB_60
  ;  a is positive 
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_59
  ;  if b is positive, go multiply!
  BRp LAB_57
  ;  a is pos, b is neg
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  BR LAB_62
  ; 
  ;  multiply 
  ; 
LAB_57
  ADD R5, R5, 1
  STR R7, R5, -1
  ;  reset sum
  AND R7, R7, 0
LAB_63
  ADD R7, R7, R0
  ADD R1, R1, -1
  BRp LAB_63
  ;  adjust sign 
  ADD R2, R2, 0
  BRz LAB_64
  NOT R7, R7
  ADD R7, R7, 1
LAB_64
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
LAB_59
  ADD R5, R5, 1
  STR R2, R5, -1
  RET
  ; 
  ;  null pointer exception 
  ; 
  ;  prints error message and exits
LAB_7
  LEA R0, LAB_65
  TRAP x22
  TRAP x25
LAB_65
.STRINGZ "Null pointer exception
"
  ; 
  ;  index out of bounds exception 
  ; 
  ;  prints error message and exits
LAB_8
  LEA R0, LAB_66
  TRAP x22
  TRAP x25
LAB_66
.STRINGZ "Index out of bounds exception
"
  ; 
  ;  add two strings 
  ; 
  ;  expects args on top of stack, puts result on stack
LAB_6
  LDR R0, R5, -2
  BRnp LAB_70
  LEA R0, LAB_72
  STR R0, R5, -2
  BR LAB_71
LAB_70
  LDR R0, R5, -1
  BRnp LAB_71
  LEA R0, LAB_72
  STR R0, R5, -1
  BR LAB_71
LAB_72
.FILL LAB_73
.FILL 5
LAB_73
.STRINGZ "null"
LAB_71
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
  BR LAB_68
LAB_69
.FILL LAB_2
LAB_67
.FILL 2
  ;  copy string 
LAB_75
.FILL LAB_74
LAB_74
  ADD R5, R5, 1
  STR R7, R5, -1
  LDR R0, R0, 0
LAB_77
  LDR R7, R0, 0
  BRz LAB_76
  STR R7, R1, 0
  ADD R0, R0, 1
  ADD R1, R1, 1
  BR LAB_77
LAB_76
  ADD R5, R5, -1
  LDR R7, R5, 0
  RET
LAB_68
  ;  invoke new method 
  LD R1, LAB_67
  ADD R0, R1, R0
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_69
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
  LD R7, LAB_75
  JSRR R7
  ;  copy second string from TMP0->0 to HP->0 
  ADD R5, R5, -1
  LDR R0, R5, 0
  LD R7, LAB_75
  JSRR R7
  ADD R5, R5, -1
  LDR R7, R5, 0
  ADD R5, R5, 1
  STR R4, R5, -1
  RET
.END
