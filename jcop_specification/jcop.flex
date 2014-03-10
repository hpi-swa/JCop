<YYINITIAL> { 
  "with"            { return sym(Terminals.LAYER_ACTIVATION); }
  "without"         { return sym(Terminals.LAYER_DEACTIVATION); }    
  "staticactive"     { return sym(Terminals.ACTIVE); }
  "contextclass"    { return sym(Terminals.CONTEXT); }
  "thislayer"       { return sym(Terminals.THIS_LAYER); }
  "superproceed"       { return sym(Terminals.SUPER_PROCEED); }
  //"on"            { return sym(Terminals.LAYER_ON); }  
  "layer"           { return sym(Terminals.LAYER); }  
  "proceed"         { return sym(Terminals.PROCEED); }  
  "before"          { return sym(Terminals.BEFORE); }
  "after"           { return sym(Terminals.AFTER); }
  //"call"            { return sym(Terminals.PC_CALL); }
  "on"              { return sym(Terminals.PC_EXECUTION); }
  //"setField"        { return sym(Terminals.PC_SET); }
  //"getField"        { return sym(Terminals.PC_GET); }
  //"cflow"           { return sym(Terminals.PC_CFLOW); }
  "when"            { return sym(Terminals.PC_IF); }
  //"within"          { return sym(Terminals.PC_WITHIN); }
  ".."              { return sym(Terminals.PC_DOTDOT); }
  "subject"         { return sym(Terminals.FORSUBJECT); }  
  "unless"         { return sym(Terminals.UNLESS); }
 }
