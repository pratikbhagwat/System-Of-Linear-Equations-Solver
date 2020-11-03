How to execute the codes:

Serial:
Execute the SerialApplication with matrix file and the vector file as the program arguments

Unoptimized Parallel:
Execute the Application with matrix file and the vector file as the program arguments

Optimized Parallel:
Execute the Application_ForDynamicNumberOfProcessors with matrix file and the vector file and the (number of available processors or the number of threads you want to spawn) as the program arguments


To test all the three cases and compare performance run
CallerProgram
Note : Make sure the data files aare in the same directory as the data files are hardcoded.
you can also change the number of times you want to execute the program for a single input. // change the variable numberOfTrials to the desired count.


Note please uncomment the following lines to see the inputs and verify the outputs in all the three programs.
//        Collections.reverse(answerArraylist);
//        verifyAnswers(args,answerArraylist);