.ORIG x3000
  ;  initialize CONST0 and CONST1
  AND R2, R2, 0
  ADD R3, R2, 1
  ;  set SFP and HP 
  LD R6, LAB_14
  LD R4, LAB_15
  BR LAB_13
  ;  data for SFP and HP
LAB_14
.FILL 32768
LAB_15
.FILL 65023
  ;  arguments to main
LAB_9
.FILL 0
LAB_13
LAB_10
  ; 
  ;  METHOD main
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  LEA R0, LAB_9
  STR R0, R6, 3
  ADD R5, R6, 4
  ;  body 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_21
LAB_20
.STRINGZ "Hello World"
LAB_18
.FILL LAB_20
.FILL 13
LAB_19
.FILL LAB_18
LAB_21
  LD R0, LAB_19
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_23
  LDR R0, R0, 0
  BR LAB_17
LAB_23
  LEA R0, LAB_22
  BR LAB_17
LAB_22
.STRINGZ "null"
  BR LAB_17
LAB_16
.FILL LAB_1
LAB_17
  TRAP x22
  LD R1, LAB_16
  JSRR R1
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
LAB_11
  ; 
  ;  METHOD test
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  ADD R5, R6, 6
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  PLUS BEGIN
  ; 
  ;  lhs 
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
  ;  rhs 
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
  ;  add integers 
  ADD R5, R5, -2
  LDR R0, R5, 0
  LDR R1, R5, 1
  ADD R0, R0, R1
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  PLUS END
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER i lhs 
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
  ;  RETURN BEGIN
  ; 
  ;  return value 
  ; 
  ;  IDENTIFIER i rhs 
  ; 
  ;  push value 
  LDR R0, R6, 5
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  ;  put in stack frame 
  STR R0, R6, 0
  ;  get return PC from stack frame
  LDR R7, R6, 2
  RET
  ; 
  ;  RETURN END
  ; 
  ; 
  ;  METHOD END test
  ; 
LAB_12
  ; 
  ;  METHOD testtest
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  ADD R5, R6, 5
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  CALL test
  ; 
  BR LAB_24
  ;  slot for SP 
LAB_25
.FILL 0
  ;  address of method to call 
LAB_26
.FILL LAB_11
LAB_24
  ;  save SP 
  ST R5, LAB_25
  ;  save SFP 
  STR R6, R5, 1
  ;  increment SP to save arguments and make space for admin area 
  ADD R5, R5, 3
  ;  push arguments 
  ;  argument 
  ; 
  ;  IDENTIFIER this rhs 
  ; 
  ;  push value 
  LDR R0, R6, 3
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  argument 
  ; 
  ;  INT CONST 21
  ; 
  LD R0, 3
  ADD R5, R5, 1
  STR R0, R5, -1
  BR LAB_27
  ;  value 
.FILL 21
LAB_27
  ; 
  ;  INT CONST END
  ; 
  ;  set new SFP (this is the old SP)
  LD R6, LAB_25
  ;  get method address and jump to it
  LD R0, LAB_26
  JSRR R0
  ;  once returned reset SP (this is the SFP)
  ADD R5, R6, 0
  ;  restore the old SFP - this was stored at offset one from the SFP
  LDR R6, R6, 1
  ;  the first cell on the stack contains the result, so increase SP by one
  ADD R5, R5, 1
  ; 
  ;  CALL END test
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER t lhs 
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
  ;  IDENTIFIER t rhs 
  ; 
  ;  push value 
  LDR R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  BR LAB_30
LAB_31
.FILL LAB_3
LAB_30
  LD R1, LAB_31
  JSRR R1
  ADD R5, R5, -1
  LDR R0, R5, 0
  BR LAB_29
LAB_28
.FILL LAB_1
LAB_29
  TRAP x22
  LD R1, LAB_28
  JSRR R1
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
  ;  METHOD END testtest
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
  LEA R1, LAB_32
  ADD R0, R0, 0
  BRzp LAB_35
  LD R7, LAB_33
  STR R7, R1, 0
  ADD R1, R1, 1
  NOT R0, R0
  ADD R0, R0, 1
LAB_35
  AND R7, R7, 0
  LD R2, LAB_36
LAB_37
  ADD R0, R0, R2
  BRn LAB_38
  ADD R7, R7, 1
  BR LAB_37
  ; 
LAB_38
  ADD R7, R7, 0
  BRz LAB_62
  LD R2, LAB_34
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_62
  LD R2, LAB_39
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_40
  LD R2, LAB_41
LAB_42
  ADD R0, R0, R2
  BRn LAB_43
  ADD R7, R7, 1
  BR LAB_42
  ; 
LAB_43
  ADD R7, R7, 0
  BRz LAB_61
  LD R2, LAB_34
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_61
  LD R2, LAB_44
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_45
  LD R2, LAB_46
LAB_47
  ADD R0, R0, R2
  BRn LAB_48
  ADD R7, R7, 1
  BR LAB_47
  ; 
LAB_48
  ADD R7, R7, 0
  BRz LAB_60
  LD R2, LAB_34
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_60
  LD R2, LAB_49
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_50
  LD R2, LAB_51
LAB_52
  ADD R0, R0, R2
  BRn LAB_53
  ADD R7, R7, 1
  BR LAB_52
  ; 
LAB_53
  ADD R7, R7, 0
  BRz LAB_59
  LD R2, LAB_34
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_59
  ADD R0, R0, 10
  ; 
  LD R7, LAB_34
LAB_55
  ADD R7, R7, R0
  STR R7, R1, 0
  ADD R1, R1, 1
  AND R2, R2, 0
  STR R2, R1, 0
  ADD R5, R5, -1
  LDR R7, R5, 0
  LEA R0, LAB_32
  ADD R5, R5, 1
  STR R0, R5, -1
  RET
  ; 
  ; data
  ; 
LAB_32
  .BLKW 7
LAB_33
.FILL 45
LAB_34
.FILL 48
LAB_36
.FILL -10000
LAB_41
.FILL -1000
LAB_46
.FILL -100
LAB_51
.FILL -10
LAB_56
.FILL -1
LAB_39
.FILL 10000
LAB_44
.FILL 1000
LAB_49
.FILL 100
LAB_54
.FILL 10
LAB_57
.FILL 1
  ; 
  ;  print newline 
  ; 
LAB_1
  ADD R5, R5, 1
  STR R7, R5, -1
  LD R0, 1
  BR LAB_63
.FILL LAB_64
LAB_64
.STRINGZ "\n"
LAB_63
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
  BRp LAB_67
  ADD R4, R4, 0
  BRp LAB_68
LAB_69
  ADD R1, R5, 0
  NOT R1, R1
  ADD R1, R1, 1
  ADD R1, R4, R1
  BRp LAB_66
  BR LAB_68
LAB_67
  ADD R4, R4, 0
  BRn LAB_66
  BR LAB_69
LAB_68
  TRAP x25
LAB_66
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
LAB_70
  STR R2, R0, 0
  ADD R0, R0, 1
  ADD R1, R1, -1
  BRp LAB_70
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
  BRz LAB_73
  ;  if a is positive, jump
  BRp LAB_74
  ;  negate a 
  NOT R0, R0
  ADD R0, R0, 1
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_73
  ;  if b is positive, jump
  BRp LAB_75
  ;  a is neg, b is too
  ;  negate b
LAB_76
  NOT R1, R1
  ADD R1, R1, 1
  ;  go, multiply!
  BR LAB_71
LAB_75
  ;  a is negative, b is positive
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  ;  go, multiply!
  BR LAB_71
LAB_74
  ;  a is positive 
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_73
  ;  if b is positive, go multiply!
  BRp LAB_71
  ;  a is pos, b is neg
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  BR LAB_76
  ; 
  ;  multiply 
  ; 
LAB_71
  ADD R5, R5, 1
  STR R7, R5, -1
  ;  reset sum
  AND R7, R7, 0
LAB_77
  ADD R7, R7, R0
  ADD R1, R1, -1
  BRp LAB_77
  ;  adjust sign 
  ADD R2, R2, 0
  BRz LAB_78
  NOT R7, R7
  ADD R7, R7, 1
LAB_78
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
LAB_73
  ADD R5, R5, 1
  STR R2, R5, -1
  RET
  ; 
  ;  null pointer exception 
  ; 
  ;  prints error message and exits
LAB_7
  LEA R0, LAB_79
  TRAP x22
  TRAP x25
LAB_79
.STRINGZ "Null pointer exception
"
  ; 
  ;  index out of bounds exception 
  ; 
  ;  prints error message and exits
LAB_8
  LEA R0, LAB_80
  TRAP x22
  TRAP x25
LAB_80
.STRINGZ "Index out of bounds exception
"
  ; 
  ;  add two strings 
  ; 
  ;  expects args on top of stack, puts result on stack
LAB_6
  LDR R0, R5, -2
  BRnp LAB_84
  LEA R0, LAB_86
  STR R0, R5, -2
  BR LAB_85
LAB_84
  LDR R0, R5, -1
  BRnp LAB_85
  LEA R0, LAB_86
  STR R0, R5, -1
  BR LAB_85
LAB_86
.FILL LAB_87
.FILL 5
LAB_87
.STRINGZ "null"
LAB_85
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
  BR LAB_82
LAB_83
.FILL LAB_2
LAB_81
.FILL 2
  ;  copy string 
LAB_89
.FILL LAB_88
LAB_88
  ADD R5, R5, 1
  STR R7, R5, -1
  LDR R0, R0, 0
LAB_91
  LDR R7, R0, 0
  BRz LAB_90
  STR R7, R1, 0
  ADD R0, R0, 1
  ADD R1, R1, 1
  BR LAB_91
LAB_90
  ADD R5, R5, -1
  LDR R7, R5, 0
  RET
LAB_82
  ;  invoke new method 
  LD R1, LAB_81
  ADD R0, R1, R0
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_83
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
  LD R7, LAB_89
  JSRR R7
  ;  copy second string from TMP0->0 to HP->0 
  ADD R5, R5, -1
  LDR R0, R5, 0
  LD R7, LAB_89
  JSRR R7
  ADD R5, R5, -1
  LDR R7, R5, 0
  ADD R5, R5, 1
  STR R4, R5, -1
  RET
.END
