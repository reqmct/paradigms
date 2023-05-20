"use strict"
function makeAbstractExpression (exp, evaluate, toString, diff, prefix, postfix) {
    exp.prototype.evaluate = evaluate;
    exp.prototype.toString = toString;
    exp.prototype.diff = diff;
    exp.prototype.prefix = prefix;
    exp.prototype.postfix = postfix;
}
function Const (number) {
    this.number = parseFloat(number);
}
makeAbstractExpression (
    Const,
    function() {
        return this.number;
    },
    function() {
        return this.number + "";
    },
    function() {
        return new Const(0);
    },
    function() {
        return this.number + "";
    },
    function() {
        return this.number + "";
    }
);
function Variable (name) {
    this.name = name;
}
Variable.prototype.names = ["x", "y", "z"];
const ZERO = new Const(0);
const ONE = new Const(1);
makeAbstractExpression (
    Variable,
    function (...args) {
        return args[this.names.indexOf(this.name)];
    },
    function() {
        return this.name
    },
    function(name) {
        return this.name === name ? ONE : ZERO; 
    },
    function() {
        return this.name
    },
    function() {
        return this.name
    }
);
function AbstractOperation (...expressions) {
    this.expressions = expressions;
}
makeAbstractExpression (
    AbstractOperation,
    function (x, y, z) {
        return this.operation(...this.expressions.map(item => item.evaluate(x, y, z)));
    },
    function () {
        return this.expressions.reduce((str, item) => str + item.toString() + " ", "") + this.operationSymbol;
    },
    function(variable) {
        return this.diffFunction(variable, ...this.expressions);
    },
    function () {
        return "(" + this.operationSymbol + (this.expressions.length === 0 ? " " :
        this.expressions.reduce((str, item) => str + " " + item.prefix(), "")) + ")";
    },
    function () {
        return "(" + this.expressions.reduce((str, item) => str + item.postfix() + " ", "") +
        (this.expressions.length === 0 ? " " : "") + this.operationSymbol + ")";
    },
);

const AllOperations = {
    operationConstuctors: {},
    operationNumberArguments: {},
    operationSymbols: [],
    add: function (operation) {
        this.operationConstuctors[operation.prototype.operationSymbol] = operation;
        this.operationSymbols.push(operation.prototype.operationSymbol);
        this.operationNumberArguments[operation.prototype.operationSymbol] = operation.prototype.number;
    },
    has: function(item) {
        return item in this.operationNumberArguments;
    },
    getConstuctor: function (operationSymbol) { return this.operationConstuctors[operationSymbol] },
    getNumber: function (operationSymbol) { return this.operationNumberArguments[operationSymbol] }
};

function makeOperation (operation, operationSymbol, diffFunction, numberArguments) {
    function Operation (...expressions) {
        AbstractOperation.call(this, ...expressions);
    }
    Operation.prototype = Object.create(AbstractOperation.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.operation = operation;
    Operation.prototype.operationSymbol = operationSymbol;
    Operation.prototype.diffFunction = diffFunction;
    Operation.prototype.number = numberArguments;
    AllOperations.add(Operation);
    return Operation;
}


function makeSpecialOperationN(n, name) {
    const sum = (...args) => {
        return AddN.prototype.operation(...args);
    }
    const sumExp = (...expressions) => {
        return new AddN(...expressions);
    }
    const sumSq = (...args) => {
        return sum(...args.map((item) => (item * item)));
    }
    const sumSqExp = (...expressions) => {
        return sumExp(...expressions.map((item) => new Multiply(item, item)));
    }

    const sumExponents = (...args) => {
        return sum(...args.map((item) => Math.pow(Math.E, item)));
    }

    const sumExponentsExp = (...expressions) => {
        return sumExp(...expressions.map((item) => new Exponent(item)));
    }


    let operation = () => null;
    let diffFunction = () => null;
    switch (name) {
        case 'sumsq':
            operation = (...args) => sumSq(...args);
            diffFunction = (variable, ...expression) => sumSqExp(...expression).diff(variable);
            break;
        case 'distance':
            operation = (...args) => Math.sqrt(sumSq(...args));
            diffFunction = (variable, ...expression) =>  new Sqrt(sumSqExp(...expression)).diff(variable);
            break;
        case 'sumexp':
            operation = (...args) => sumExponents(...args);
            diffFunction = (variable, ...expression) => sumExponentsExp(...expression).diff(variable);
            break;
        case 'lse':
            operation = (...args) => Math.log(sumExponents(...args));
            diffFunction = (variable, ...expression) => new Ln(new Sumexp(...expression)).diff(variable);
            break;

    }
    return makeOperation(operation, n !== -1 ? name + n : name, diffFunction, n);
}

const Add = makeOperation (
    (left, right) => left + right,
    "+",
    (variable, left, right) => new Add(left.diff(variable), right.diff(variable)),
    2
);

const AddN = makeOperation (
    (...exp) => exp.reduce((ans, item) => ans + item, 0),
    "AddN",
    (variable, ...expression) => expression.reduce((ans, item) => new Add(ans, item.diff(variable)), new Const(0)),
    -1
);

const Subtract = makeOperation (
    (left, right) => left - right,
    "-",
    (variable, left, right) => new Subtract(left.diff(variable), right.diff(variable)),
    2
);

const Multiply = makeOperation (
    (left, right) => left * right,
    "*",
    (variable, left, right) => new Add(new Multiply(left.diff(variable), right), new Multiply(left, right.diff(variable))),
    2
);


const Divide = makeOperation (
    (left, right) => left / right,
    "/",
    (variable, left, right) => new Divide(
        new Subtract(new Multiply(left.diff(variable), right), new Multiply(left, right.diff(variable))),
        new Multiply(right, right)
    ),
    2
);


const Negate = makeOperation (
    (exp) => -exp,
    "negate",
    (variable, exp) => new Negate(exp.diff(variable)),
    1
);

const Sqrt = makeOperation (
    (exp) => Math.sqrt(exp),
    "sqrt",
    (variable, exp) => new Divide(exp.diff(variable), new Multiply(new Const(2), new Sqrt(exp))),
    1
);

const Exponent = makeOperation(
    (exp) =>  Math.pow(Math.E, exp),
    "exponent",
    (variable, exp) => new Multiply(exp.diff(variable), new Sumexp(exp)),
    1
);

const Ln = makeOperation(
    (exp) =>  Math.log(exp),
    "ln",
    (variable, exp) => new Divide(exp.diff(variable), exp),
    1
);

const Sumsq2 = makeSpecialOperationN(2, "sumsq");
const Sumsq3 = makeSpecialOperationN(3, "sumsq");
const Sumsq4 = makeSpecialOperationN(4, "sumsq");
const Sumsq5 = makeSpecialOperationN(5, "sumsq");
const Distance2 =  makeSpecialOperationN(2, "distance");
const Distance3 =  makeSpecialOperationN(3, "distance");
const Distance4 =  makeSpecialOperationN(4, "distance");
const Distance5 =  makeSpecialOperationN(5, "distance");
const Sumexp = makeSpecialOperationN(-1, "sumexp");
const LSE = makeSpecialOperationN(-1, "lse");

function parse (expression) {
    const doOperation = function (array, n, item) {
        let args = array.splice(array.length - n, n);
        return new (AllOperations.getConstuctor(item))(...args);
    }
    const updateArray = function (array, item) {
        if (Variable.prototype.names.includes(item)) {
            array.push(new Variable(item));
        } else if (AllOperations.has(item)) {
            array.push(doOperation(array, AllOperations.getNumber(item), item));
        } else {
            array.push(new Const(item));
        }
        return array;
    } 
    return  expression.split(" ").filter((s) => s !== "").reduce((array, item) => updateArray(array, item), []).pop();
}

//-----------------------------------------------Exception----------------------------------------------

class IllegalArgumentError extends Error {
    constructor(message) {
        super(message);
        this.name = "IllegalArgumentError";
    }
}

class BaseParser {
    constructor(source, mod) {
        this.source = source;
        this.mod = mod;
        this.pos = 0;
        this.END = '\0';
        this.next();
    }
    next() {
        this.ch = this.pos < this.source.length ? this.source[this.pos++] : this.END;
    }
    test(expected) {
        return this.ch === expected;
    }
    take(expected) {
        if (this.test(expected)) {
            this.next();
            return true;
        }
        return false;
    }
    expect(expected) {
        let value = "";
        for (let i = 0; i < expected.length; i++) {
            if (this.eof() || this.isSpace()) {
                break;
            }
            if (this.ch !== this.END) {
                value += this.ch;
            }
            this.next();
        }
        if(value !== expected) {
            this.makeError(expected, value);
        }
        return true;
    }
    checkToken(expected) {
        let startPos = this.pos - 1;
        let res = true;
        for(let c of expected) {
            if (this.eof() || this.isSpace()) {
                res = false;
                break;
            }
            if(c !== this.ch){
                res = false;
                break;
            }
            this.next();
        }
        this.pos = startPos;
        this.next();
        return res;
    }
    getToken(f) {
        let startPos = this.pos - 1;
        let res = "";
        while(!this.eof()) {
            if (f(this.ch)) {
                break;
            }
            res += this.ch;
            this.next();
        }
        this.pos = startPos;
        this.next();
        return res;
    }
    makeError(expected, token) {
        throw new IllegalArgumentError("Incorrect token: " + token + ". Position: " + this.pos + ". Maybe it should be: " + expected + ".");
    }
    between(start, finish) {
        return start <= this.ch && this.ch <= finish;
    }
    isDigit() {
        return this.between('0', '9');
    }
    isLetter() {
        return /[a-z]/i.test(this.ch);
    }
    getCh() {
        return this.ch;
    }
    eof() {
        return this.test(this.END);
    }
    getPos() {
        return this.pos;
    }
    isSpace() {
        return this.test(' ');
    }
    getMod() {
        return this.mod;
    }
}

class AbstractParser extends BaseParser {
    constructor(source, mod) {
        super(source, mod);
    }
    parse() {
        const ans = this.parseArgument();
        if(!super.eof()) {
            throw new IllegalArgumentError("Unexpected character: " + super.getCh() + ". Poosition: " + super.getPos() + ". Invalid expression.");
        }
        return ans[0];
    }
    parseBrackets() {
        this.skipSpace();
        // :NOTE: expect?
        super.expect('(');
        return this.parseArguments();
    }
    getAllOperationInArgs(operationIndexes, args) {
        let res = ""
        for(let index of operationIndexes) {
            res += args[index].prototype.operationSymbol + " ";
        }
        return res;
    }
    parseArguments() {
        this.skipSpace();
        let args = [];
        let operationIndexes = [];
        while(!super.test(')')) {
            const argument = this.parseArgument();
            args.push(argument[0]);
            if(argument[1] === true) {
                operationIndexes.push(args.length - 1);
            }
        }
        super.expect(')')
        if (operationIndexes.length != 1) {
            throw new IllegalArgumentError("Inccorect nubmer of operations: " + this.getAllOperationInArgs(operationIndexes, args)
            + ". It should be 1.");
        }
        let operationConstuctor;
        switch (super.getMod()) {
            case 'pre':
                if(operationIndexes[0] != 0) {
                    throw new IllegalArgumentError("Inccorect position of operation: " + 
                    this.getAllOperationInArgs(operationIndexes, args)
                    + ". It should be first.");
                }
                operationConstuctor = args[0];
                args = args.slice(1);
                break;
            case 'post': 
                if(operationIndexes[0] != args.length - 1) {
                    throw new IllegalArgumentError("Inccorect position of operation: " + 
                    this.getAllOperationInArgs(operationIndexes, args)
                    + ". It should be last.");
                }
                operationConstuctor = args[args.length - 1];
                args = args.slice(0, -1);
                break;
        }
        if(args.length !== operationConstuctor.prototype.number && operationConstuctor.prototype.number !== -1) {
            throw new IllegalArgumentError("The operation '" + operationConstuctor.prototype.operationSymbol +
             "' has the wrong number of argumets: " + args.length);
        }
        return new operationConstuctor(...args);
    }
    parseArgument() {
        this.skipSpace();
        let argument;
        let isOperation = false;
        if (super.eof()) {
            throw new IllegalArgumentError("Unexpected end of file" + ". Maybe it should be: '('.");
        }
        if (this.isConst()) {
            argument = this.parseConst();
        } else if(this.isOperation()) {
            argument = this.parseOperation();
            isOperation = true;
        } else if(super.test('(')) {
            argument = this.parseBrackets();
        } else if(super.isLetter()) {
            argument = this.parseVariable();
        } else {
            throw new IllegalArgumentError("Incorrect argument: " + super.getCh() + ". Position: " + super.getPos() + ". Maybe it should be bracket.");
        }
        this.skipSpace();
        return [argument, isOperation];
    }
    isCorrectToken(f, token) {
        if(!f(super.getCh())) {
            throw new IllegalArgumentError("Incorrect token format: '" + token + this.getCh()
             + "'. Maybe it should be: '" + token + "'.");
        }
    }
    isConst() {
        this.skipSpace();
        const isNumeric = (n) => !isNaN(n) && n.length !== 0;
        let cnst = super.getToken((c) => c === ' ' || c === '(' || c === ')');
        return isNumeric(cnst);
    }
    parseConst() {
        this.skipSpace();
        let cnst = super.getToken((c) => c === ' ' || c === '(' || c === ')');
        super.expect(cnst);
        return new Const(cnst);
    }
    parseVariable() {
        this.skipSpace();
        let variable = "";
        while(super.isLetter()) {
            variable += super.getCh();
            super.next();
        }
        if(!Variable.prototype.names.includes(variable)) {
            throw new IllegalArgumentError("Inccorect variable name: " + variable + ". Position: " + super.getPos());
        }
        return new Variable(variable);
    }
    isOperation() {
        this.skipSpace();
        for (let operation of AllOperations.operationSymbols) {
            if (super.checkToken(operation)) {
                return true;
            }
        }
        return false;
    }
    parseOperation() {
        this.skipSpace();
        for (let operation of AllOperations.operationSymbols) {
            if (super.checkToken(operation)) {
                super.expect(operation);
                this.isCorrectToken((c) => c === ' ' || c === '(' || c === ')', 
                                    operation);
                return AllOperations.getConstuctor(operation);
            }
        }
        throw new IllegalArgumentError("Operation not found");
    }
    skipSpace() {
        while(super.take(' ')) {
        }
    }
}
const parsePrefix = (source) => new AbstractParser(source, 'pre').parse();
const parsePostfix = (source) => new AbstractParser(source, 'post').parse();