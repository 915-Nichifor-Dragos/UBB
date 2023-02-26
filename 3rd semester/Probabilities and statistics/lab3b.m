p = input("The probability is: ");

if p < 0.05 || p > 0.95
    fprintf("Error");
    return;
end

for n=1:3:100
    x = n * p;
    y = sqrt(n * p * (1 - p));
    plot(0:n, binopdf(0:n,n,p), 0:n, normpdf(0:n, x, y));
    pause(0.5);
end