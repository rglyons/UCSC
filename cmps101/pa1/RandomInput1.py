#------------------------------------------------------------------------------
#
#  RandomInput1.py
#  Creates a matched pair of random input-output files for cmps 101 pa1 and pa2 
#  by mixing up the lines in this file. To run this program do: 
#
#                 python3 RandomInput1.py 
#
#------------------------------------------------------------------------------
from random import shuffle

# get file names and open files
infile = open(input('Enter input file name: '),'w')
outfile = open(input('Enter output file name: '),'w')

# to mix-up some other file put its name in place of 'RandomInput1.py'
thisfile = open('RandomInput1.py','r')  

# read lines of this file into a list and randomize the list
L = thisfile.readlines()
shuffle(L)

# write mixed lines to infile
for line in L:
   infile.write(line)
   
# sort list and write lines to outfile
L.sort()
for line in L:
   outfile.write(line)

# close files   
infile.close()
outfile.close()
thisfile.close()
