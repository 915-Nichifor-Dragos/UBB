%{

H0 : null hypothesis
H1 : alternative hypothesis
-----------------------------------
H0 : theta = theta0
vs
H1 : { theta < theta0 (left-tailed test)    
       theta > theta0 (right-tailed test)
       theta != theta0 (two-tailed test)
     }

alpha in (0, 1) significance level
TS statistics test
TS0 = TS (theta = theta0) observed value
RR = rejection region
P - value = (if alpha < P, DO NOT REJECT H0)

* When do we reject H0?

1) hypothesis testing: if TS0 is in RR, reject H0
                       if TS0 not in TT, do not reject H0
2) significance testing: if alpha >= P, reject H0
                         if alpha < P, do not reject H0


%}