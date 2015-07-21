<YYINITIAL> { 
	"with"            { return sym(Terminals.LAYER_ACTIVATION); }
	"without"         { return sym(Terminals.LAYER_DEACTIVATION); }
	"swap"         { return sym(Terminals.LAYER_SWAPPING); }
    "swappable"         { return sym(Terminals.SWAPPABLE); }    
	"staticactive"     { return sym(Terminals.ACTIVE); }
	"contextclass"    { return sym(Terminals.CONTEXT); }
	"thislayer"       { return sym(Terminals.THIS_LAYER); }
	"superproceed"       { return sym(Terminals.SUPER_PROCEED); }
	"layer"           { return sym(Terminals.LAYER); }  
	"proceed"         { return sym(Terminals.PROCEED); }  
	"before"          { return sym(Terminals.BEFORE); }
	"after"           { return sym(Terminals.AFTER); }
	"on"              { return sym(Terminals.PC_EXECUTION); }
	"when"            { return sym(Terminals.PC_IF); }
	".."              { return sym(Terminals.PC_DOTDOT); }
	"subject"         { return sym(Terminals.FORSUBJECT); }  
	"unless"         { return sym(Terminals.UNLESS); }
	"requires"			 { return sym(Terminals.REQUIRES); }
}
