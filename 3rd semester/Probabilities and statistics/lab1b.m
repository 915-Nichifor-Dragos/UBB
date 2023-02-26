x = 0 : 0.01 : 3;

f1 = x.^5 / 10;
f2 = x .* sin(x);
f3 = cos(x);

subplot (3, 1, 1)
plot(x, f1, '--red')
subplot (3, 1, 2)
plot(x, f2, '.-green')
subplot (3, 1, 3)
plot(x, f3, ':magenta')
