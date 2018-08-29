#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Sat Jan  6 18:58:28 2018

@author: LouisMaestrati
"""

file_coord='Resultat.txt'

 
from numpy import *
import matplotlib.pyplot as plt
 
# ouverture du fichier texte 
f=open(file_coord,'r')
lignes =f.read().split("  ")
f.close()

lignes[0]=1
n=len(lignes)
l=[1]
for i in range (1,n):
    tcurrentstring=lignes[i].split("\n")
    if "." in tcurrentstring[0]:
        l.append(float(tcurrentstring[0]))
    else:
        l.append(int(tcurrentstring[0]))
    if (len(tcurrentstring)==2 and len(tcurrentstring[1])>0):
        if "." in tcurrentstring[1]:
            l.append(float(tcurrentstring[1]))
        else:
            l.append(int(tcurrentstring[1]))

M =[]
n2=len(l)
for i in range (int(n2/5)):
    M.append([l[5*i],l[5*i+1],l[5*i+2],l[5*i+3],l[5*i+4]])
    
x=[]
y=[]
N=len(M)
for i in range (N):
    x.append(M[i][0])
    y.append(M[i][3])
#0: itérations,1: meilleur chemin, 2:indice du meilleur chemin, 3:long dernier chemin, 4:temps calcul
fig, ax = plt.subplots()
#ax.loglog(x, y,'o-',basex=10)
ax.plot(x,y,'')
ax.set(xlabel='nombre itérations', ylabel='long dernier chemin',
       title='PMX selectiondeterm transposition')
ax.grid()
plt.show()  
    