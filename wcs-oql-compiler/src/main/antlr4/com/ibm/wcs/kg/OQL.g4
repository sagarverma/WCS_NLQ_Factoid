grammar OQL;

// FATMA: should we allow UNION ALL and UNION (one preserves duplicates, other eliminates? We can call UNION DISTINCT and UNION to be clearer than SQL

nestedQuery: oqlQuery | oqlQuery UNION DISTINCT? nestedQuery;
oqlQuery:  select from where? groupBy? having? orderBy? fetch? offset?;
select: SELECT DISTINCT? selectColumn (COMMA selectColumn)*;
selectColumn: selectExpr  (AS variable)?;
funcCall:   funcName LPAREN DISTINCT? selectExpr (COMMA selectExpr)*  RPAREN;
oqlIdentifier:  aliasref DOT property ;
constantVal:   bool | NUMERIC_LITERAL | NULL | STRING_LITERAL;

from: FROM  fromArgument (COMMA fromArgument)*; 
fromArgument: LPAREN  nestedQuery RPAREN alias |   concept alias;

//FATMA: Why are we strictly ordering path expressions, and why introduce yet another keyword PATH??
//where: WHERE orExpr? (PATH pathExpr (AND pathExpr)*)?; 
//		| WHERE PATH pathExpr (AND pathExpr)*; // strict ordering between pathexpr and orExpr


where: WHERE orExpr;
orExpr:  andExpr  (OR andExpr)*;  // precedence  PAREN > NOT > AND > OR 
andExpr:  notExpr  (AND notExpr)*;
notExpr:  NOT? parenExpr;
parenExpr:  LPAREN bExpr RPAREN | bExpr;
bExpr:     compExpr |  LPAREN orExpr RPAREN | pathExpr;
compExpr: uExpr binop  uExpr;
listExpr:  LPAREN uExpr (COMMA uExpr)* RPAREN ;
selectExpr:  funcCall | oqlIdentifier |  constantVal | LPAREN  nestedQuery RPAREN ;
uExpr:  selectExpr |  listExpr| variableref;



pathExpr: objExpr EQ joinType? objExpr;
objExpr: object ('->' relationName)*;
object:  aliasref |  LPAREN nestedQuery RPAREN aliasref;

groupBy:  GROUP BY oqlIdentifier (COMMA oqlIdentifier)*;
orderBy: ORDER BY orderByArgument (COMMA orderByArgument)*;
orderByArgument: (oqlIdentifier|variable) ORDERTYPE?;
having: HAVING orExpr;
fetch: FETCH FIRST NUMERIC_LITERAL ROWS ONLY;
offset: OFFSET NUMERIC_LITERAL;

variable: QUOTED_STRING_LITERAL|ID;
variableref: QUOTED_STRING_LITERAL|ID;
aliasref: ID;
alias:  ID;
concept: ID;
property: ID;
funcName: ID;
relationName: ID;



binop:  LE | GE | LT | GT | EQ | CONTAINS | IN | NOT IN | LIKE | MATCH | NE;

bool: TRUE | FALSE;

/*************** LEX ********************/ 

GROUPBY: GROUP BY;
ORDERBY: ORDER BY;
ORDERTYPE: ASC | DESC;
PATH: 'path' | 'PATH';
joinType: LEFT OUTER JOIN | RIGHT OUTER JOIN | INNER JOIN | OUTER JOIN;


AND: 'and' | 'AND' | '&&';
OR: 'or' | 'OR' | '||';
LE: 'le' | '<=' | 'LE';
GE: 'ge' | '>=' |  'GE';
LT: 'lt' | '<' | 'LT';
GT:'gt' | '>' | 'GT';
EQ: '=' | 'eq' | 'EQ';
NE: 'ne'|'<>'|'NE';
CONTAINS: '~';
IN: 'in' | 'IN';
LIKE: 'like' | 'LIKE';
MATCH: 'match' | 'MATCH';
NOT: 'not' | 'NOT';

SORT: 'sort' | 'SORT';
DISTINCT: 'distinct' | 'DISTINCT';
AS: 'as' | 'AS';
NULL: 'null' | 'NULL';
ROWS: 'rows' | 'ROWS';
ONLY: 'only' | 'ONLY';
FETCH: 'fetch' | 'FETCH';
OFFSET: 'offset' | 'OFFSET';
FIRST: 'first' | 'FIRST';
SELECT: 'select' | 'SELECT' | 'Select';
HAVING: 'having' | 'HAVING' | 'Having';
FROM : 'from' | 'FROM';
WHERE: 'where' | 'WHERE';
GROUP: 'group' | 'Group' | 'GROUP';
ORDER: 'order' | 'Order' | 'ORDER';
UNION: 'union'|'Union'|'UNION';

BY: 'by'|'By' | 'BY';

ASC: 'asc' | 'ASC';
DESC: 'desc' | 'DESC';
LEFT: 'left' | 'LEFT' | 'Left';
RIGHT: 'right' | 'RIGHT' | 'Right';
JOIN: 'join' | 'JOIN' | 'Join';
OUTER: 'outer' | 'OUTER' | 'Outer';
INNER: 'inner' | 'INNER' | 'Inner';


DOT: '.';
COMMA: ',';
ARROW: '->';
LPAREN: '(';
RPAREN: ')';

TRUE: 'true' | 'TRUE';
FALSE: 'false' | 'FALSE';

ID
 : [a-zA-Z_] [a-zA-Z_0-9]* 
 ;
 
NUMERIC_LITERAL
 : [-+]? DIGIT+ ( '.' DIGIT* )? ( E [-+]? DIGIT+ )?
 | '.' DIGIT+ ( E [-+]? DIGIT+ )?
 ;

STRING_LITERAL
 : '\'' ( ~'\'' | '\'\'' )* '\''
 ;
 QUOTED_STRING_LITERAL 
 : '"' ( ~'\'' | '\'\'' )* '"'
 ;

fragment DIGIT : [0-9];

fragment NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t'|NEWLINE)+ {skip();} ;
fragment E : [eE];

