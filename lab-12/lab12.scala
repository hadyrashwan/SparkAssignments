/**
INSTRUCTIONS: 
1. PLEASE SOLVE WHERE IT SAYS //SOVLE:
2. Do NOT change the variable names, we assume that once we run the script, the
following 10 variables are going to be avialble and correctly filled with data 
hits
userreqs
reversedReqs
freqcount
userip
userips
accounts
accounthits
accountsByPCode
namesByPCode
3. We have provided all the printing code for you so you can debug your RDDs
uncomment them so you can see the results of your code.  

4. you can open a spark shell and then run this fille using :load lab12.scala
**/


//A function to help you print a simple RDD
//prints the first 5 elements from a file in an rdd
import org.apache.spark.rdd.RDD
def mydebug[T](title: String, rdd:RDD[T], n:Int=5){
   println(s"======= $title =======")
   rdd.take(n)foreach(println)
}

// Step 1 - Create an RDD based on a subset of weblogs (those ending in digit 6)
val logs=sc.textFile("/loudacre/weblogs/*")

// -----------------------------------------------------------------------------
// Step1 : map each request (line) to a pair (userid, 1) & Sum for each request
// -----------------------------------------------------------------------------

//---- Step1.a
val hits = logs.map(x=>(x.split(' ')(2),1))//SOVLE:
//--uncomment to print 
//mydebug("hits", hits)

//---- Step1.b
val userreqs = hits.reduceByKey(_+_)//SOVLE:

//--uncomment to print 
//mydebug("userreqs", userreqs)


// -----------------------------------------------------------------------------
// Step 2 - return a user count for each hit frequency
// -----------------------------------------------------------------------------
//---- Step2.a
val reversedReqs = userreqs.map(x=>(x._2,x._1)) //SOVLE:

//--uncomment to print 
//mydebug("reversedReqs", reversedReqs)


//---- Step2.b
val freqcount = reversedReqs.countByKey()//SOVLE:
//--uncomment to print 
//mydebug("freqcount", freqcount)

// -----------------------------------------------------------------------------
// Step 3 - Group IPs by user ID
// -----------------------------------------------------------------------------
//---- Step3.a, get from each line the (user, ip)
val userip = logs.map(x=>(x.split(' ')(2),x.split(' ')(0))) //SOVLE:
//--uncomment to print 
//mydebug("userip", userip)

    
//---- Step3.b, group IPs by user ID
val userips=  userip.groupByKey() //SOVLE:
//--uncomment to print 
//mydebug("userip", userip)


// print out the first 10 user ids, and their IP list (first 2)
//--uncomment to print 
//println(s"======= userips =======")
// for (pair <- userips.take(10)) {
//    println(pair._1 + ":")
//    for (ip <- pair._2.take(2)) 
//       println("\t"+ip)
// }

// -----------------------------------------------------------------------------
// Step 4 Group IPs by user ID
// -----------------------------------------------------------------------------
val accountsdata = "/loudacre/accounts/*"  // something wrong here

// Step 4a - map account data to (userid,[values....])
val accounts = sc.textFile(accountsdata).map(x=> (x.split(',')(0),x.split(',')))//SOVLE:
//--uncomment to print 
//println(s"======= accounts =======")
// for (a <- accounts.take(5)) {
//  printf("%s, [%s] \n",a._1,a._2.mkString(","))

// }

// Step 4b - Join account data with userreqs then merge hit count into valuelist   
val accounthits = accounts.join(hits)//SOVLE:

// Step 4c - Display userid, hit count, first name, last name for the first few elements
// WE SOLVED THIS FOR YOU. just look at how we index the tuples and arrays.
//--uncomment to print 
// for (pair <- accounthits.take(10)) {
//    printf("%s, %s, %s, %s\n",pair._1,pair._2._2, pair._2._1(3),pair._2._1(4))
// }

   
// Challenge 1: key accounts by postal/zip code
val accountsByPCode = accounts.map(y=>(y.split(',')(8),y)//SOVLE:
//--uncomment to print 
//println(s"======= accountsByPCode =======")
// for (a <- accountsByPCode.take(5)) {
//  printf("%s, [%s] \n",a._1,a._2.mkString(","))

// }

// Challenge 2: map account data to lastname,firstname  
val namesByPCode = null//SOLVE;

// Challenge 3: print the first 5 zip codes and list the names 
// WE SOLVED THIS FOR YOU. just look at how we index the tuples and arrays.
// We did it 2 way, so you can see different Sytles

//--uncomment to print 
//println(s"======= namesByPCode, style1 =======")
// for (pair <- namesByPCode.take(5)) {
//  printf("%s, [%s] \n",pair._1,pair._2.mkString(","))
// }

//--uncomment to print 
// println(s"======= namesByPCode, style2 =======")
// for (pair <- namesByPCode.sortByKey().take(5)) {
//    println("---" + pair._1)
//    pair._2.take(3)foreach(println)
// }
