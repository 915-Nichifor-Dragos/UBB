x = 0 : 0.01 : 3;

f1 = x.^5 / 10;
f2 = x .* sin(x);
f3 = cos(x);

plot(x, f1, '--red', x, f2, '.-green', x, f3, ':magenta');
title('Function graph')
legend('x^5/10', 'x*sin(x)', 'cos(x)')
