clc
clear all

% FIRST LABORATORY
%{
% 1.1

A = [1, 0, -2 ; 2, 1, 3 ; 0, 1, 0;];
B = [2, 1, 1 ; 1, 0, -1 ; 1, 1, 0;];

C = A - B;
D = A * B;
E = A .* B;

% 1.2

x = 0 : 0.01 : 3;

fa = x .^ 5 / 10;
fb = x .* sin(x);
fc = cos(x);

plot(x, fa, 'green', x, fb, 'red', x, fc, 'blue');
title('Graph functions')
legend('x^5/10', 'x*sin(x)', 'cos(x)')

% 1.3

y = 0 : 0.01 : 3;

subplot(3, 1, 1)
plot(y, fa, 'green')
title('First function')
legend('x^5/10')

subplot(3, 1, 2)
plot(y, fb, 'red')
title('Second function')
legend('x*sin(x)')

subplot(3, 1, 3)
plot(y, fc, 'blue')
title('Third function')
legend('cos(x)')

% SECOND LABORATORY

clear all

% 2a

binopdf(0:3, 3, 0.5)

% 2b

binocdf(0:3, 3, 0.5)

% 2c

fprintf("P(x=0)=%f \n", binopdf(0, 3, 0.5));
fprintf("P(x!=1)=%f \n", 1 - binopdf(1, 3, 0.5))

%2d

fprintf("P(x<=2)=%f \n", binocdf(2, 3, 0.5))
fprintf("P(x<2)=%f \n", binocdf(1, 3, 0.5))

%2e

fprintf("P(x>=1)=%f \n", 1 - binopdf(0, 3, 0.5))
fprintf("P(x>1)=%f \n", 1 - binocdf(1, 3, 0.5))


%2f

toss1 = randi([0, 1]);
toss2 = randi([0, 1]);
toss3 = randi([0, 1]);

nrheads = toss1 + toss2 + toss3;
probability = nrheads / 3;

fprintf("The number of heads: %d; probability: %f \n", nrheads, probability)

% THIRD LABORATORY

clear all

%1
%command = input ("Please enter the distribution type: normal/Student/Fisher \n", "s");

switch command

    case "normal"
        niu = input("Niu=");
        fprintf("\n")
        sigma = input("Sigma=");
        fprintf("\n")

        %a
        fprintf("P(x<=0)= %f \n", normcdf(0, niu, sigma))
        fprintf("P(x>=0)= %f \n", 1 - normcdf(0, niu, sigma ))

        %b
        fprintf("P(-1<=x<=1)= %f \n", normcdf(1, niu, sigma) - normcdf(-1, niu, sigma))
        fprintf("P(x<=-1 or x>=1)= %f \n", 1 - (normcdf(1, niu, sigma) - normcdf(-1, niu, sigma)))

        %c
        alpha = input("Alpha= ");
        fprintf("\n")
        if (alpha >= 1 || alpha <= 0)
            fprintf("Error!")
        end
        fprintf("P(x < xa) = %f has xa=%f \n", alpha, norminv(alpha, niu, sigma))

        %d
        beta = input("Beta= ");
        fprintf("\n")
        if (beta >= 1 || beta <= 0)
            fprintf("Error!")
        end
        fprintf("P(x > xb) = %f has xb=%f \n", beta, 1 - norminv(beta, niu, sigma))


    case "Student"
        n = input ("n= ");
        fprintf("\n")

        %a
        fprintf("P(x<=0)= %f \n", tcdf(0, n))
        fprintf("P(x>=0)= %f \n", 1 - tcdf(0, n))

        %b
        fprintf("P(-1<=x<=1)= %f \n", tcdf(1, n) - tcdf(-1, n))
        fprintf("P(x<=-1 or x>=1)= %f \n", 1 - (tcdf(1, n) - tcdf(-1, n)))

        %c
        alpha = input("Alpha= ");
        fprintf("\n")
        if (alpha >= 1 || alpha <= 0)
            fprintf("Error!")
        end
        fprintf("P(x < xa) = %f has xa=%f \n", alpha, tinv(alpha, n))

        %d
        beta = input("Beta= ");
        fprintf("\n")
        if (beta >= 1 || beta <= 0)
            fprintf("Error!")
        end
        fprintf("P(x > xb) = %f has xb=%f \n", beta, 1 - tinv(beta, n))

    case "Fisher"
        n = input ("n= ");
        fprintf("\n")
        m = input ("m= ");

        %a
        fprintf("P(x<=0)= %f \n", fcdf(0, n, m))
        fprintf("P(x>=0)= %f \n", 1 - fcdf(0, n, m))

        %b
        fprintf("P(-1<=x<=1)= %f \n", fcdf(1, n, m) - fcdf(-1, n, m))
        fprintf("P(x<=-1 or x>=1)= %f \n", 1 - (fcdf(1, n, m) - fcdf(-1, n, m)))

        %c
        alpha = input("Alpha= ");
        fprintf("\n")
        if (alpha >= 1 || alpha <= 0)
            fprintf("Error!")
        end
        fprintf("P(x < xa) = %f has xa=%f \n", alpha, finv(alpha, n, m))

        %d
        beta = input("Beta= ");
        fprintf("\n")
        if (beta >= 1 || beta <= 0)
            fprintf("Error!")
        end
        fprintf("P(x > xb) = %f has xb=%f \n", beta, 1 - finv(beta, n, m))
end

%2a
p = input ("The probability is: ");
fprintf("\n")

if (p < 0.05 || p > 0.95)
    fprintf("Error")
    return
end

for n = 1:3:100
    
    niu = n * p;
    sigma = sqrt(n * p * (1 - p));
    plot(0:n, binopdf(0:n, n, p), "red", 0:n, normpdf(0:n, niu, sigma), 'blue')
    pause(0.5)

end

%2c
p = input("The probability is: ");
fprintf("\n")
n = input ("n= ");
fprintf("\n")

if (n < 30 || p > 0.05)
    fprintf("Error")
    return
end

lambda = n * p;
plot(0:n, binopdf(0:n, n, p), "red", 0:n, poisspdf(0:n, lambda), "blue")


% LABORATORY 4

%2a
clc
p = input("Probability of success: ");
n = input("Number of trials: ");

U = rand(1, n);
X = (U < p);

U_X = unique(X);
N_X = hist(X, U_X);

relative_frequency = N_X / n;

[U_X ; relative_frequency]

%2b
clc
p = input("Probability of success: ");
N = input("Number of simulations: ");
n = input("Number of trials for each simulation: ");

for i=1:N
    X(i) = 0;
    for j=1:n
        U = rand;
        X(i) = X(i) + (U < p);
    end
end
U_X = unique(X);
N_X = hist(X, length(U_X));

relative_freq = N_X / N;
[U_X;relative_freq]

plot(U_X, relative_freq, 'o', 0:N, binopdf(0:N, n, p), 'x')

%2c
clc
p = input("The probability is: ");
N = input("The number of simulations: ");

for i=1:N
    X(i) = 0;
    while(true)
        U = rand;

        if (U <= p)
            break
        end

        if (U > p)
            X(i) = X(i) + 1;
        end
    end
end

U_X = unique(X);
n_X = hist(X, length(U_X));

relative_freq = n_X / N;

[U_X ; relative_freq]

plot(U_X, relative_freq, 'o', 0:N, geopdf(0:N,p), 'x')

%d
clc
p = input("The probability is: ");
N = input("The number of simulations: ");
n = input("The number of trials: ");

for i=1:N
    X(i) = 0;
    for j=1:n
        while(true)
            U = rand;
            if (U < p)
                break
            else
                X(i) = X(i) + 1;
            end
        end
    end
end

U_X = unique(X);
n_X = hist(X, length(U_X));

relative_freq = n_X / N;

[U_X ; relative_freq]

plot(U_X, relative_freq, 'o', 0:N, nbinpdf(0:N,n, p), 'x')

% LABORATORY 5

X = [20, 21, 22, 23, 24, 25, 26, 27 ; 2, 1, 3, 6, 5, 9, 2, 2];
Y = [75, 76, 77, 78, 79, 80, 81, 82 ; 3, 2, 2, 5, 8, 8, 1, 1];

m1 = mean(X);
m2 = mean(Y);
v1 = var(X);
v2 = var(Y);
covariance = cov(X, Y);
corcoef = corrcoef(X, Y);

clc
%B1

X = [7 7 4 5 9 9 ...
     4 12 8 1 8 7 ...
     3 13 2 1 17 7 ...
     12 5 6 2 1 13 ...
     14 10 2 4 9 11 ...
     3 5 12 6 10 7];

confidence_level = input("The confidence level is: ");
alpha = 1 - confidence_level;
xbar = mean(X);
n = length(X);

% Variance is known
sigma = 5;

q1 = norminv(1 - alpha/2, 0, 1);
q2 = norminv(alpha/2, 0, 1);

c1 = xbar - q1 * sigma / sqrt(n);
c2 = xbar - q2 * sigma / sqrt(n);

fprintf("The confidence interval with when the variance is known is: [%f - %f]. \n", c1, c2);

% Variance is unknown

standard_deviation = std(X);

q3 = tinv(1 - alpha/2, n - 1);
q4 = tinv(alpha/2, n - 1);

c3 = xbar - q3 * standard_deviation / sqrt(n);
c4 = xbar - q4 * standard_deviation / sqrt(n);

fprintf("The confidence interval with when the variance is unknown is: [%f - %f]. \n", c3, c4);

% For variance and standard deviation

q5 = chi2inv(1 - alpha/2, n - 1);
q6 = chi2inv(alpha/2, n - 1);

c5 = (n - 1) * standard_deviation * standard_deviation / q5;
c6 = (n - 1) * standard_deviation * standard_deviation / q6;

fprintf("The confidence interval for the variance is: [%f - %f]. \n", c5, c6);
fprintf("The confidence interval for the standard deviation is: [%f - %f]. \n", sqrt(c5), sqrt(c6));

clc
%B2

X1 = [22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
X2 = [17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];

m1 = mean(X1);
m2 = mean(X2);
v1 = var(X1);
v2 = var(X2);
n1 = length(X1);
n2 = length(X2);

sp = sqrt(((n1 - 1) * v1 + (n2 - 1) * v2)/(n1 + n2 - 2));

confidence_level = input("The confidence level is: ");
alpha = 1 - confidence_level;

%a) sigma1 = sigma2

q1 = tinv(1 - alpha/2, n1 + n2 - 2);
q2 = tinv(alpha/2, n1 + n2 - 2);

c1 = m1 - m2 - q1 * sqrt ((1/n1) + (1/n2)) * sp;
c2 = m1 - m2 - q2 * sqrt ((1/n1) + (1/n2)) * sp;

fprintf("Confidence interval for the means for unknown sigma1 = sigma2 is : [%f - %f].\n", c1, c2);

%b) sigma1 != sigma2

c = (v1/n1) / (v1/n1 + v2/n2);
n = 1 / (c^2 / (n1 - 1) + (1 - c)^2 / (n2 - 1));
q3 = tinv(1 - alpha/2, n);
q4 = tinv(alpha/2, n);

c3 = m1 - m2 - q1 * sqrt(v1/n1 + v2/n2);
c4 = m1 - m2 - q2 * sqrt(v1/n1 + v2/n2);

fprintf("Confidence interval for the means for unknown sigma1 != sigma2 is : [%f - %f].\n", c3, c4);

%c) CI for variances and standard deviation

f1 = finv(1- alpha/2, n1-1, n2-1);
f2 = finv(alpha/2, n1-1, n2-1);

c5 = 1 / f1 * v1/v2;
c6 = 1 / f2 * v1/v2;

fprintf("Confidence interval for the variance is : [%f - %f].\n", c5, c6);
fprintf("Confidence interval for the standard deviation is : [%f - %f].\n", sqrt(c5), sqrt(c6));


% LABORATORY 6

clc

x = [7 7 4 5 9 9 4 12 8 1 8 7 3 13 2 1 17 7 12 5 6 2 1 13 14 10 2 4 9 11 3 5 12 6 10 7];
sigma = 5;

alpha = input("The significance level is: ");

m0 = 9;

% H0 - the null hypothesis: m = m0
% H1 - the alternative hypothesis: m < m0. This is a left tailed test for m

[H, P, CI, ZVAL] = ztest(x, m0, sigma, 'alpha', alpha, 'tail', 'left');
RR = [-inf, norminv(alpha)];
fprintf("The confidence interval is: %f - %f\n", CI)
fprintf("The rejection region is: %f - %f\n", RR)
fprintf("The P-value of the test is: %f\n", P)
fprintf("The value of the test statistic is: %f\n", ZVAL)

if H == 1
    fprintf("The null hypothesis has been rejected\n");
    fprintf("The test suggests that the standard is NOT met.\n")
else
    fprintf("The null hypothesis has NOT been rejected\n");
    fprintf("The test suggests that the standard is met.\n")
end

n = length(x);
m1 = 5.5;

% H0 - null hypothesis: m = m1
% H1 - alternative hypothesis: m > m1. This is a right tailed test for m

[H, P, CI, ZVAL] = ttest(x, m1, 'alpha', alpha, 'tail', 'right');
RR = [tinv(1 - alpha, n - 1), inf];

fprintf("The confidence interval is: %f - %f\n", CI);
fprintf("The rejection region is: %f - %f\n", RR);
fprintf("The P-value of the test is: %f\n", P);
fprintf("The value of the test statistics is: %f\n", ZVAL.tstat)

if H == 1
    fprintf("The null hypothesis has been rejected\n")
    fprintf("The test suggests that the average exceeds 5.5\n")
else
    fprintf("The null hypothesis has NOT been rejected\n")
    fprintf("The test suggests that the average DOES NOT exceed 5.5\n")
end

%}

pr = [22.4 21.7 24.5 23.4 21.6 23.3 22.4 21.6 24.8 20.0];
rg = [17.7 14.8 19.6 19.6 12.1 14.8 15.4 12.6 14.0 12.2];
alpha = input("The significance level is: ");
n1 = length(pr);
n2 = length(rg);

q1 = finv(alpha/2, n1 - 1, n2 - 1);
q2 = finv(1 - alpha/2, n1 - 1, n2 - 1);
% H0 - the null hypothesis: the variances are equal.
% H1 - the alternative hypothesis: the variances are different
[H, P, CI, ZVAL] = vartest2(pr, rg, 'alpha', alpha);
fprintf("The confidence interval is (%f, %f)\n", CI)
fprintf("The rejection region is (-inf, %f) U (%f, inf)\n", q1, q2)
fprintf("The value of the test statistic is %f\n", ZVAL.fstat)
fprintf("The P-value of the test is %f\n", P)

if H == 0
    fprintf("The null hypotheses has not been rejected\n")
    fprintf("The variances of the 2 populations seem to be equal\n")
    % case sigma1 = sigma2, unknown
    q3 = tinv(1 - alpha, n1 + n2 - 2);
    % H0 - the null hypothesis: the regular gas milage is higher than premium gas
    % H1 - the alternative hypothesis: the premium gas milage is higher than regular gas
    [H2, P2, CI2, ZVAL2] = ttest2(pr, rg, 'Alpha', alpha, 'Tail', 'right');
    fprintf("The confidence interval is (%f, %f)\n", CI)
    fprintf("The rejection region is (%f, inf)\n", q3)
    fprintf("The value of the test statistic is %f\n", ZVAL2.tstat)
    fprintf("The P-value of the test is %f\n", P)
    if H2 == 0
        fprintf("The null hypothesis has not been rejected")
        fprintf("The regular gasoline has higher gas milage than premium gasoline")
    else
        fprintf("The null hypothesis has been rejected\n")
        fprintf("The premium gasoline has higher gas milage than regular gasoline\n")
    end
else
    fprintf("The null hypotheses has been rejected\n")
    fprintf("The variances of the 2 populations do not seem to be equal\n")
    % case sigma1 != sigma2, unknown
    v1 = var(pr);
    v2 = var(rg);
    c = (v1/n1)/(v1/n1 + v2/n2);
    n = 1/(c^2/(n1 - 1) + (1 - c)^2/(n2 - 1));
    q4 = tinv(1 - alpha, n);
    [H2, P2, CI2, ZVAL2] = ttest(pr, rg, 'Alpha', alpha, 'Tail', 'right');
    fprintf("The confidence interval is (%f, %f)\n", CI)
    fprintf("The rejection regions is (-inf, %f)\n", q4)
    fprintF("The value of the test statistic is %f\n", ZVAL.tstat)
    fprintf("The P-value of the test is %f\n", P)
    if H2 == 0
        fprintf("The null hypothesis has not been rejected\n")
        fprintf("The regular gasoline has higher has milage than premium gasoline\n")
    else
        fprintf("The null hypothesis has been rejected\n")
        fprintf("The premium gasoline has higher gas milage than premium gasoline\n")
    end
end












