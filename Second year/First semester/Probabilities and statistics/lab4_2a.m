clear all;
clc;
p=input('prob. of succes = ');

N=input('number of simulations = ');

U = rand(1,N);
X = (U<p);

%for i=1:N
%    U=rand;
%    X(i) = (U<p);
%end

U_X = unique(X);
U_X
n_X = hist(X, length(U_X));
n_X
relative_freq = n_X/N;

[U_X;relative_freq]