command = input("normal / Student / Fischer\n", "s");

switch command
    case "normal"
        mu = input("Niu: ");
        fprintf("\n");
        sigma = input("Sigma: ");

        % A)
        smallerOrEqual0 = normcdf(0, mu, sigma);
        biggerOrEqual0 = 1 - smallerOrEqual0;
        fprintf("P(x<=0) = %f\nP(x>=0) = %f\n", smallerOrEqual0, biggerOrEqual0);

        % B)
        b1 = normcdf(1, mu, sigma) - normcdf(-1, mu, sigma);
        b2 = 1 - b1;
        fprintf("P(-1<=x<=1) = %f\nP(x<=-1) or P(x>= 1) = %f\n", b1, b2);

        % C)
        alpha = input("Alpha: ");
        if (alpha >+ 1 || alpha <+ 0)
            fprintf("Error")
        end
        x = norminv(alpha, mu, sigma);
        fprintf("Xa = %f\n", x);

        % D)
        beta = input("Beta: ");
        if (beta >+ 1 || beta <+ 0)
            fprintf("Error")
        end
        y = norminv(1 - beta, mu, sigma);
        fprintf("Xb = %f\n", y);

    case "Student"
        n = input("n: ");
        fprintf("\n");

         % A)
        smallerOrEqual0 = tcdf(0, n);
        biggerOrEqual0 = 1 - smallerOrEqual0;
        fprintf("P(x<=0) = %f\nP(x>=0) = %f\n", smallerOrEqual0, biggerOrEqual0);

        % B)
        b1 = tcdf(1, n) - tcdf(-1, n);
        b2 = 1 - b1;
        fprintf("P(-1<=x<=1) = %f\nP(x<=-1) or P(x>= 1) = %f\n", b1, b2);

        % C)
        alpha = input("Alpha: ");
        if (alpha >+ 1 || alpha <+ 0)
            fprintf("Error")
        end
        x = tinv(alpha, n);
        fprintf("Xa = %f\n", x);

        % D)
        beta = input("Beta: ");
        if (beta >+ 1 || beta <+ 0)
            fprintf("Error")
        end
        y = 1 - tinv(beta, n);
        fprintf("Xb = %f\n", y);

    case "Fischer"
        n = input("n: ");
        fprintf("\n");
        m = input("m: ");

         % A)
        smallerOrEqual0 = fcdf(0, n, m);
        biggerOrEqual0 = 1 - smallerOrEqual0;
        fprintf("P(x<=0) = %f\nP(x>=0) = %f\n", smallerOrEqual0, biggerOrEqual0);

        % B)
        b1 = fcdf(1, n, m) - fcdf(-1, n, m);
        b2 = 1 - b1;
        fprintf("P(-1<=x<=1) = %f\nP(x<=-1) or P(x>= 1) = %f\n", b1, b2);

        % C)
        alpha = input("Alpha: ");
        if (alpha >+ 1 || alpha <+ 0)
            fprintf("Error")
        end
        x = finv(alpha, n, m);
        fprintf("Xa = %f\n", x);

        % D)
        beta = input("Beta: ");
        if (beta >+ 1 || beta <+ 0)
            fprintf("Error")
        end
        y = 1 - finv(beta, n, m);
        fprintf("Xb = %f\n", y);
end



