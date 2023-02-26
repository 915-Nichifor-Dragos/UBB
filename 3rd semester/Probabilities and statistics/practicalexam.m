clc

A = [1021 980 1017 988 1005 998 1014 985 995 1004 1030 1015 995 1023];
B = [1070 970 993 1013 1006 1002 1014 997 1002 1010 975];

n1 = length(A);
n2 = length(B);

% a)
% alpha - the significance level = 5%
alpha = 0.05;

% Compute the quantiles for the rejection region of the two tailed test
f1 = finv(alpha/2, n1-1, n2-1);  
f2 = finv(1-alpha/2, n1-1, n2-1);

% Execute a two-tailed test
% H0 (null hypothesis) - the variances are equal
% H1 (alternative hypothesis) - the variances are different
[H, P, CI, ZVAL] = vartest2(A, B, "alpha", alpha);

fprintf('The rejection region for F is (%6.4f, %6.4f) U (%6.4f, %6.4f)\n', -inf, f1, f2, inf)
fprintf('The value of the test statistic F is %6.4f\n', ZVAL.fstat)
fprintf('The P-value for the variances test is %6.4f\n', P)

% H=0 => null hypothesis is not rejected
% H=1 => null hypothesis is rejected
if H==0
    fprintf('The null hypothesis is NOT rejected.\n')
    fprintf('The variances seem to be equal.\n')
else
    fprintf('The null hypothesis is rejected.\n')
    fprintf('The variances seem to differ.\n')
end

% b)

% 95 confidence interval means that the significance level is equal to 5
% At subpoint a we found out that the variances are equal

% The means of the 2 populations
m1 = mean(A);
m2 = mean(B);

% The variances of the 2 populations
v1 = var(A);
v2 = var(B);

if H==0
    n = n1 + n2 - 2;
    t = icdf('t', 1 - alpha/2, n);
    rad = sqrt(1/n1 + 1/n2);
    sp = sqrt(((n1 - 1)*v1 + (n2 - 1)*v2)/n);
    li = m1 - m2 - t * sp * rad;
    ri = m1 - m2 + t * sp * rad;
    fprintf("The confidence interval for the difference of average weights (means) is: (%f, %f)", li, ri)
else    
    c = (v1/n1) / (v1/n1 + v2/n2);
    n = 1 / (c^2/(n1 - 1) + (1 - c)^2/(n2 - 1));
    t = icdf('t', 1 - alpha / 2, n);
    rad = sqrt(v1/n1 + v2/n2);
    li = m1 - m2 - t*rad;
    ri = m1 - m2 + t*rad;
    fprintf("The confidence interval for the difference of average weights (means) is: (%f, %f)", li, ri)
end