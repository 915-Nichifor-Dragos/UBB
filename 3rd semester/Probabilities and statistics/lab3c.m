n = input("n = ");
p = input("p = ");

if p > 0.05 || n < 30
    fprintf("Error")
    return
end

x = n * p;
y = sqrt(n * p * (1 - p));

lambda = n * p;
plot(0:n, binopdf(0:n, n, p), 0:n, normpdf(0:n, x, y))
