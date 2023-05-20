const variableNames = {"x": 0, "y": 1, "z": 2};
const operation = (f) => (...args) => (...vars) => f(...args.map(item => item(...vars)));
const cnst = (value) => () => parseFloat(value);
const variable = (name) => (...args) => args[variableNames[name]];
const add = operation((left, right) => left + right);
const subtract = operation((left, right) => left - right);
const multiply = operation((left, right) => left * right);
const divide = operation((left, right) => left / right);
const negate = operation((exp) => -exp);
const floor = operation((exp) => Math.floor(exp));
const ceil = operation((exp) => Math.ceil(exp));
const madd = operation((left, middle, right) => left * middle + right);

const test = add(subtract(multiply(variable("x"), variable("x")), 
multiply(cnst(2), variable("x"))), cnst(1));

for (let x = 0; x <= 10; x++) {
    console.log(x * x - 2 * x + 1, test(x, 0, 0));
}

const one = cnst(1);
const two = cnst(2);

const operations = {
    "+": [add, 2],
    "-": [subtract, 2],
    "*": [multiply, 2],
    "/": [divide, 2],
    "*+": [madd, 3],
    "negate": [negate, 1],
    "_": [floor, 1],
    "^": [ceil, 1]
};

const constans = {
    "one": one,
    "two": two
};

function parse(expression) {
    const makeOperation = (array, f, n) => {
        return f(...array.splice(0, n).reverse());
    }
    const makeConst = (constItem) => {
        if (constItem in constans) {
            return constans[constItem];
        }
        return cnst(constItem);
    }
    const updateArray = (array, item) => {
        if (item in variableNames) {
            array.unshift(variable(item));
        } else if (item in operations) {
            array.unshift(makeOperation(array, ...operations[item]));
        } else {
            array.unshift(makeConst(item));
        }
        return array;
    } 
    return  expression.split(" ").filter((s) => s !== "").reduce((array, item) => updateArray(array, item), []).shift();
}
console.log(parse("x x 2 - * x * 1 +")(5, 0, 0));