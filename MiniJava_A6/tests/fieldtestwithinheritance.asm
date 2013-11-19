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
  ADD R5, R6, 6
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  NEW BEGIN 
  ; 
  BR LAB_17
  ;  address of allocation routine 
LAB_18
.FILL LAB_2
  ;  size of object to allocate 
LAB_16
.FILL 1
  ;  load address and size and invoke new method 
LAB_17
  LD R0, LAB_16
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_18
  JSRR R1
  ;  new HP is address, is on stack
  ; 
  ;  NEW END 
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER a lhs 
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
  ;  NEW BEGIN 
  ; 
  BR LAB_20
  ;  address of allocation routine 
LAB_21
.FILL LAB_2
  ;  size of object to allocate 
LAB_19
.FILL 2
  ;  load address and size and invoke new method 
LAB_20
  LD R0, LAB_19
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_21
  JSRR R1
  ;  new HP is address, is on stack
  ; 
  ;  NEW END 
  ; 
  ;  lhs 
  ; 
  ;  IDENTIFIER b lhs 
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
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_25
LAB_24
.STRINGZ "Hello World"
LAB_22
.FILL LAB_24
.FILL 13
LAB_23
.FILL LAB_22
LAB_25
  LD R0, LAB_23
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN lhs
  ; 
  ;  get adress 
  ; 
  ;  IDENTIFIER a rhs 
  ; 
  ;  push value 
  LDR R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  LDR R0, R5, -1
  BRnp LAB_26
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_26
  ; 
  ;  SELECTOR END 
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
  ;  SELECTOR BEGIN rhs
  ; 
  ;  get adress 
  ; 
  ;  IDENTIFIER a rhs 
  ; 
  ;  push value 
  LDR R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  LDR R0, R5, -1
  BRnp LAB_29
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_29
  ADD R5, R5, -1
  LDR R0, R5, 0
  ;  get value from address and push
  LDR R0, R0, 0
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_31
  LDR R0, R0, 0
  BR LAB_28
LAB_31
  LEA R0, LAB_30
  BR LAB_28
LAB_30
.STRINGZ "null"
  BR LAB_28
LAB_27
.FILL LAB_1
LAB_28
  TRAP x22
  LD R1, LAB_27
  JSRR R1
  ; 
  ;  CALL test
  ; 
  BR LAB_32
  ;  slot for SP 
LAB_33
.FILL 0
  ;  address of method to call 
LAB_34
.FILL LAB_11
LAB_32
  ;  save SP 
  ST R5, LAB_33
  ;  save SFP 
  STR R6, R5, 1
  ;  increment SP to save arguments and make space for admin area 
  ADD R5, R5, 3
  ;  push arguments 
  ;  argument 
  ; 
  ;  IDENTIFIER a rhs 
  ; 
  ;  push value 
  LDR R0, R6, 4
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  set new SFP (this is the old SP)
  LD R6, LAB_33
  ;  get method address and jump to it
  LD R0, LAB_34
  JSRR R0
  ;  once returned reset SP (this is the SFP)
  ADD R5, R6, 0
  ;  restore the old SFP - this was stored at offset one from the SFP
  LDR R6, R6, 1
  ; 
  ;  CALL END test
  ; 
  ; 
  ;  CALL test2
  ; 
  BR LAB_35
  ;  slot for SP 
LAB_36
.FILL 0
  ;  address of method to call 
LAB_37
.FILL LAB_12
LAB_35
  ;  save SP 
  ST R5, LAB_36
  ;  save SFP 
  STR R6, R5, 1
  ;  increment SP to save arguments and make space for admin area 
  ADD R5, R5, 3
  ;  push arguments 
  ;  argument 
  ; 
  ;  IDENTIFIER b rhs 
  ; 
  ;  push value 
  LDR R0, R6, 5
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  IDENTIFIER END 
  ; 
  ;  set new SFP (this is the old SP)
  LD R6, LAB_36
  ;  get method address and jump to it
  LD R0, LAB_37
  JSRR R0
  ;  once returned reset SP (this is the SFP)
  ADD R5, R6, 0
  ;  restore the old SFP - this was stored at offset one from the SFP
  LDR R6, R6, 1
  ; 
  ;  CALL END test2
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
LAB_11
  ; 
  ;  METHOD test
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  ADD R5, R6, 4
  ;  body 
  ; 
  ;  PLUS BEGIN
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN rhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_39
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_39
  ADD R5, R5, -1
  LDR R0, R5, 0
  ;  get value from address and push
  LDR R0, R0, 0
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
  ; 
  ;  rhs 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_43
LAB_42
.STRINGZ " "
LAB_40
.FILL LAB_42
.FILL 3
LAB_41
.FILL LAB_40
LAB_43
  LD R0, LAB_41
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  add strings 
  LD R0, 1
  BR LAB_44
.FILL LAB_6
LAB_44
  JSRR R0
  ; 
  ;  PLUS END
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_46
  LDR R0, R0, 0
  BR LAB_38
LAB_46
  LEA R0, LAB_45
  BR LAB_38
LAB_45
.STRINGZ "null"
LAB_38
  TRAP x22
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_50
LAB_49
.STRINGZ "Blaaaah."
LAB_47
.FILL LAB_49
.FILL 10
LAB_48
.FILL LAB_47
LAB_50
  LD R0, LAB_48
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN lhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_51
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_51
  ; 
  ;  SELECTOR END 
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
  ;  SELECTOR BEGIN rhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_54
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_54
  ADD R5, R5, -1
  LDR R0, R5, 0
  ;  get value from address and push
  LDR R0, R0, 0
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_56
  LDR R0, R0, 0
  BR LAB_53
LAB_56
  LEA R0, LAB_55
  BR LAB_53
LAB_55
.STRINGZ "null"
  BR LAB_53
LAB_52
.FILL LAB_1
LAB_53
  TRAP x22
  LD R1, LAB_52
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
  ;  METHOD END test
  ; 
LAB_12
  ; 
  ;  METHOD test2
  ; 
  ;  save SFP R7 and initialize SP R5
  STR R7, R6, 2
  ADD R5, R6, 4
  ;  body 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_60
LAB_59
.STRINGZ "asdasdas"
LAB_57
.FILL LAB_59
.FILL 10
LAB_58
.FILL LAB_57
LAB_60
  LD R0, LAB_58
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN lhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_61
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_61
  ADD R5, R5, -1
  LDR R0, R5, 0
  ADD R0, R0, R3
  ; push address
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
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
  ;  CALL test
  ; 
  BR LAB_62
  ;  slot for SP 
LAB_63
.FILL 0
  ;  address of method to call 
LAB_64
.FILL LAB_11
LAB_62
  ;  save SP 
  ST R5, LAB_63
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
  ;  set new SFP (this is the old SP)
  LD R6, LAB_63
  ;  get method address and jump to it
  LD R0, LAB_64
  JSRR R0
  ;  once returned reset SP (this is the SFP)
  ADD R5, R6, 0
  ;  restore the old SFP - this was stored at offset one from the SFP
  LDR R6, R6, 1
  ; 
  ;  CALL END test
  ; 
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_68
LAB_67
.STRINGZ "brrrrr"
LAB_65
.FILL LAB_67
.FILL 8
LAB_66
.FILL LAB_65
LAB_68
  LD R0, LAB_66
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN lhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_69
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_69
  ; 
  ;  SELECTOR END 
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
  ;  CALL test
  ; 
  BR LAB_70
  ;  slot for SP 
LAB_71
.FILL 0
  ;  address of method to call 
LAB_72
.FILL LAB_11
LAB_70
  ;  save SP 
  ST R5, LAB_71
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
  ;  set new SFP (this is the old SP)
  LD R6, LAB_71
  ;  get method address and jump to it
  LD R0, LAB_72
  JSRR R0
  ;  once returned reset SP (this is the SFP)
  ADD R5, R6, 0
  ;  restore the old SFP - this was stored at offset one from the SFP
  LDR R6, R6, 1
  ; 
  ;  CALL END test
  ; 
  ; 
  ;  SELECTOR BEGIN rhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_75
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_75
  ADD R5, R5, -1
  LDR R0, R5, 0
  ADD R0, R0, R3
  ;  get value from address and push
  LDR R0, R0, 0
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_77
  LDR R0, R0, 0
  BR LAB_74
LAB_77
  LEA R0, LAB_76
  BR LAB_74
LAB_76
.STRINGZ "null"
  BR LAB_74
LAB_73
.FILL LAB_1
LAB_74
  TRAP x22
  LD R1, LAB_73
  JSRR R1
  ; 
  ;  ASSIGN 
  ; 
  ;  rhs 
  ; 
  ;  STRING CONST BEGIN 
  ; 
  BR LAB_81
LAB_80
.STRINGZ "Gaaaah."
LAB_78
.FILL LAB_80
.FILL 9
LAB_79
.FILL LAB_78
LAB_81
  LD R0, LAB_79
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  STRING CONST END 
  ; 
  ;  lhs 
  ; 
  ;  SELECTOR BEGIN lhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_82
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_82
  ADD R5, R5, -1
  LDR R0, R5, 0
  ADD R0, R0, R3
  ; push address
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
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
  ;  SELECTOR BEGIN rhs
  ; 
  ;  get adress 
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
  LDR R0, R5, -1
  BRnp LAB_85
  LD R0, 1
  JSRR R0
.FILL LAB_7
LAB_85
  ADD R5, R5, -1
  LDR R0, R5, 0
  ADD R0, R0, R3
  ;  get value from address and push
  LDR R0, R0, 0
  ADD R5, R5, 1
  STR R0, R5, -1
  ; 
  ;  SELECTOR END 
  ; 
  ADD R5, R5, -1
  LDR R0, R5, 0
  BRz LAB_87
  LDR R0, R0, 0
  BR LAB_84
LAB_87
  LEA R0, LAB_86
  BR LAB_84
LAB_86
.STRINGZ "null"
  BR LAB_84
LAB_83
.FILL LAB_1
LAB_84
  TRAP x22
  LD R1, LAB_83
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
  ;  METHOD END test2
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
  LEA R1, LAB_88
  ADD R0, R0, 0
  BRzp LAB_91
  LD R7, LAB_89
  STR R7, R1, 0
  ADD R1, R1, 1
  NOT R0, R0
  ADD R0, R0, 1
LAB_91
  AND R7, R7, 0
  LD R2, LAB_92
LAB_93
  ADD R0, R0, R2
  BRn LAB_94
  ADD R7, R7, 1
  BR LAB_93
  ; 
LAB_94
  ADD R7, R7, 0
  BRz LAB_118
  LD R2, LAB_90
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_118
  LD R2, LAB_95
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_96
  LD R2, LAB_97
LAB_98
  ADD R0, R0, R2
  BRn LAB_99
  ADD R7, R7, 1
  BR LAB_98
  ; 
LAB_99
  ADD R7, R7, 0
  BRz LAB_117
  LD R2, LAB_90
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_117
  LD R2, LAB_100
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_101
  LD R2, LAB_102
LAB_103
  ADD R0, R0, R2
  BRn LAB_104
  ADD R7, R7, 1
  BR LAB_103
  ; 
LAB_104
  ADD R7, R7, 0
  BRz LAB_116
  LD R2, LAB_90
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_116
  LD R2, LAB_105
  ADD R0, R0, R2
  ; 
  AND R7, R7, 0
LAB_106
  LD R2, LAB_107
LAB_108
  ADD R0, R0, R2
  BRn LAB_109
  ADD R7, R7, 1
  BR LAB_108
  ; 
LAB_109
  ADD R7, R7, 0
  BRz LAB_115
  LD R2, LAB_90
  ADD R7, R7, R2
  STR R7, R1, 0
  ADD R1, R1, 1
LAB_115
  ADD R0, R0, 10
  ; 
  LD R7, LAB_90
LAB_111
  ADD R7, R7, R0
  STR R7, R1, 0
  ADD R1, R1, 1
  AND R2, R2, 0
  STR R2, R1, 0
  ADD R5, R5, -1
  LDR R7, R5, 0
  LEA R0, LAB_88
  ADD R5, R5, 1
  STR R0, R5, -1
  RET
  ; 
  ; data
  ; 
LAB_88
  .BLKW 7
LAB_89
.FILL 45
LAB_90
.FILL 48
LAB_92
.FILL -10000
LAB_97
.FILL -1000
LAB_102
.FILL -100
LAB_107
.FILL -10
LAB_112
.FILL -1
LAB_95
.FILL 10000
LAB_100
.FILL 1000
LAB_105
.FILL 100
LAB_110
.FILL 10
LAB_113
.FILL 1
  ; 
  ;  print newline 
  ; 
LAB_1
  ADD R5, R5, 1
  STR R7, R5, -1
  LD R0, 1
  BR LAB_119
.FILL LAB_120
LAB_120
.STRINGZ "\n"
LAB_119
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
  BRp LAB_123
  ADD R4, R4, 0
  BRp LAB_124
LAB_125
  ADD R1, R5, 0
  NOT R1, R1
  ADD R1, R1, 1
  ADD R1, R4, R1
  BRp LAB_122
  BR LAB_124
LAB_123
  ADD R4, R4, 0
  BRn LAB_122
  BR LAB_125
LAB_124
  TRAP x25
LAB_122
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
LAB_126
  STR R2, R0, 0
  ADD R0, R0, 1
  ADD R1, R1, -1
  BRp LAB_126
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
  BRz LAB_129
  ;  if a is positive, jump
  BRp LAB_130
  ;  negate a 
  NOT R0, R0
  ADD R0, R0, 1
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_129
  ;  if b is positive, jump
  BRp LAB_131
  ;  a is neg, b is too
  ;  negate b
LAB_132
  NOT R1, R1
  ADD R1, R1, 1
  ;  go, multiply!
  BR LAB_127
LAB_131
  ;  a is negative, b is positive
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  ;  go, multiply!
  BR LAB_127
LAB_130
  ;  a is positive 
  ADD R1, R1, 0
  ;  if one is zero we're done
  BRz LAB_129
  ;  if b is positive, go multiply!
  BRp LAB_127
  ;  a is pos, b is neg
  ;  set flag to 1 for negative result
  ADD R2, R2, 1
  BR LAB_132
  ; 
  ;  multiply 
  ; 
LAB_127
  ADD R5, R5, 1
  STR R7, R5, -1
  ;  reset sum
  AND R7, R7, 0
LAB_133
  ADD R7, R7, R0
  ADD R1, R1, -1
  BRp LAB_133
  ;  adjust sign 
  ADD R2, R2, 0
  BRz LAB_134
  NOT R7, R7
  ADD R7, R7, 1
LAB_134
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
LAB_129
  ADD R5, R5, 1
  STR R2, R5, -1
  RET
  ; 
  ;  null pointer exception 
  ; 
  ;  prints error message and exits
LAB_7
  LEA R0, LAB_135
  TRAP x22
  TRAP x25
LAB_135
.STRINGZ "Null pointer exception
"
  ; 
  ;  index out of bounds exception 
  ; 
  ;  prints error message and exits
LAB_8
  LEA R0, LAB_136
  TRAP x22
  TRAP x25
LAB_136
.STRINGZ "Index out of bounds exception
"
  ; 
  ;  add two strings 
  ; 
  ;  expects args on top of stack, puts result on stack
LAB_6
  LDR R0, R5, -2
  BRnp LAB_140
  LEA R0, LAB_142
  STR R0, R5, -2
  BR LAB_141
LAB_140
  LDR R0, R5, -1
  BRnp LAB_141
  LEA R0, LAB_142
  STR R0, R5, -1
  BR LAB_141
LAB_142
.FILL LAB_143
.FILL 5
LAB_143
.STRINGZ "null"
LAB_141
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
  BR LAB_138
LAB_139
.FILL LAB_2
LAB_137
.FILL 2
  ;  copy string 
LAB_145
.FILL LAB_144
LAB_144
  ADD R5, R5, 1
  STR R7, R5, -1
  LDR R0, R0, 0
LAB_147
  LDR R7, R0, 0
  BRz LAB_146
  STR R7, R1, 0
  ADD R0, R0, 1
  ADD R1, R1, 1
  BR LAB_147
LAB_146
  ADD R5, R5, -1
  LDR R7, R5, 0
  RET
LAB_138
  ;  invoke new method 
  LD R1, LAB_137
  ADD R0, R1, R0
  ADD R5, R5, 1
  STR R0, R5, -1
  LD R1, LAB_139
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
  LD R7, LAB_145
  JSRR R7
  ;  copy second string from TMP0->0 to HP->0 
  ADD R5, R5, -1
  LDR R0, R5, 0
  LD R7, LAB_145
  JSRR R7
  ADD R5, R5, -1
  LDR R7, R5, 0
  ADD R5, R5, 1
  STR R4, R5, -1
  RET
.END
