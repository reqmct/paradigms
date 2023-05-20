prime(N) :- prime_table(N), !.
prime(2) :- !.
prime(3) :- !.
prime(N) :- N > 1,	mod(N, 2) =\= 0,
not is_not_primitive(N, 3), assert(prime_table(N)).
is_not_primitive(Curr, Div) :- mod(Curr, Div) =:= 0, !.
is_not_primitive(Curr, Div) :- NewDiv is Div + 2, NewDiv * NewDiv =< Curr,  is_not_primitive(Curr, NewDiv).

composite(N) :- not prime(N).
prime_divisors(N, Divs) :- number(N), !, factorization(2, N, Divs).
prime_divisors(N, Divs) :- list_is_sorted(Divs), list_is_prime(Divs), mul_list(Divs, N).

factorization(Curr, 1, []) :- !.
factorization(Curr, N, Divs) :- mod(N, Curr) =:=0, !, NewN is N // Curr, factorization(Curr, NewN, NewDivs), Divs = [Curr | NewDivs].
factorization(Curr, N, Divs) :- SquareCurr is Curr * Curr, NewCurr is Curr + 1, SquareCurr =< N, !, factorization(NewCurr, N, Divs).
factorization(_, N, [N]).

list_is_sorted([]) :- !.	
list_is_sorted([_]) :- !.
list_is_sorted([PrevHead, Head | Tail]) :- PrevHead =< Head, list_is_sorted([Head | Tail]).

list_is_prime([]) :- !.
list_is_prime ([Head | Tail]) :- prime(Head), list_is_prime(Tail).

mul_list([], 1) :- !.
mul_list([Head | Tail], R) :- mul_list(Tail, NewR), R is NewR * Head.

convert_divs(Prev, 0, [], []) :- !.
convert_divs(Prev, Score, [H | Divs], PairDivs) :- Prev =:= H, !, convert_divs(Prev, NewScore, Divs, PairDivs), Score is NewScore + 1.
convert_divs(Prev, 0, [H | Divs], PairDivs) :- convert_divs(H, Score, Divs, NewPairDivs),
NewScore is Score + 1, PairDivs = [(H, NewScore) | NewPairDivs].

add_new_divs(Div, 0, OldDivs, OldDivs) :- !.
add_new_divs(Div, Score, NewDivs, OldDivs) :- NewScore is Score - 1,
add_new_divs(Div, NewScore, AddDivs, OldDivs), NewDivs = [Div | AddDivs].

convert_pairdivs([], []) :- !.
convert_pairdivs(Divs, [(Prev, Score) | PairDivs]) :- convert_pairdivs(NewDivs, PairDivs), add_new_divs(Prev, Score, Divs, NewDivs).

compact_prime_divisors(N, PairDivs) :- number(N), !, prime_divisors(N, Divs), convert_divs(-1, Score, Divs, PairDivs).
compact_prime_divisors(N, PairDivs) :- convert_pairdivs(Divs, PairDivs), prime_divisors(N, Divs).