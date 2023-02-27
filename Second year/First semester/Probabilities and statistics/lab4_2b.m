clear all;
clc;

p=input('prob. of succes = ');

if (p > 1)
    fprintf('p - between 0 and 1');
    return;
end

N=input('number of simulations = ');
n=input('number of trials = ');

for i = 1:N
    X(i) = 0;
    for j = 1:n
        U = rand;
        X(i) = X(i) + (U < p);
    end
end

U_X = unique(X);
n_X = hist(X, length(U_X));
relative_freq = n_X/N;

[U_X;relative_freq]

plot(U_X, relative_freq, 'x', 0:n, binopdf(0:n,n,p), 'o')

