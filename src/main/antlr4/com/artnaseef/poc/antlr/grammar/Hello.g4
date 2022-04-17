grammar Hello;

// ORIGINAL EXAMPLE (swallows whitespace):
//r: 'hello' WS ID ;
//ID: [a-z]+ ;
//WS: [ \t\n\r]+ -> skip ;

// UPDATED (captures whitespace as a token)
helloFile: 'START' WS ( helloStatement WS ) * 'END' ;
helloStatement: 'hello' WS ID ;
ID: [a-z]+ ;
WS: [ \t\n\r]+ ;
