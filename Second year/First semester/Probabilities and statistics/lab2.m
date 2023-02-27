% Coin tossing problem

%a

clear all

pdf('bino', 0:3, 3, 1/2)
binopdf(0:3, 3, 1/2)

% ---------------------------------------------

%x = 0:3;
%plot (x, sin(x))
%x = 0:0.5:3;
%plot (x, sin(x))

% ---------------------------------------------

%b

binocdf(2, 3, 1/2)

% ---------------------------------------------

%x = 0:0.01:3;

%plot (x, sin(x), x, cos(x))

% plot (x, sin(x))
% hold on
% plot (x, cos(x))
% %...
% hold off

% ---------------------------------------------

clear all

x = 0:3;
k = 0:0.01:3;

pdf = binopdf(x, 3, 1/2);
cdf = binocdf (k, 3, 1/2);

plot (x, pdf)
hold on
plot (k, cdf)
hold off

%c

clear all

fprintf("F(x == 0) is %f\n", binopdf(0, 3, 1/2));
fprintf("F(x!=1) is %f\n", 1 - binopdf(0, 3, 1/2));

%d

fprintf("F(X <= 2) is %f\n", binocdf(2, 3, 1/2));
fprintf("F(X < 2) is %f\n", binocdf(1, 3, 1/2));

%e

clear all

fprintf("F(X >= 1) is %f\n", 1 - binopdf(0, 3, 1/2));
fprintf("F(X > 1) is %f\n", 1 - binocdf(1, 3, 1/2));

%f

clear all

p1 = randi([0, 1]);
p2 = randi([0, 1]);
p3 = randi([0, 1]);

s = p1 + p2 + p3;
p = s/3;
fprintf("The probability is: %f\n", p);

