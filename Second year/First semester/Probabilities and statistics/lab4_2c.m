clear all;
clc;

p=input('prob. of succes = ');

if (p > 1)
    fprintf('p - between 0 and 1');
    return;
end

n=input('number of simulations = ');

for i = 1:n
    X(i) = 0;
    while (true)
        U = rand;
        if (U < p)
            X(i) = X(i) + 1;
        else
            break
        end
    end
end

U_X = unique(X);
n_X = hist(X, length(U_X));
relative_freq = n_X/n;

[U_X;relative_freq];

plot(U_X, relative_freq, 'x', 0:20, geopdf(0:20,p), 'o')
