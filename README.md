# Gen_Algo_Salesman_Problem

Les différentes méthodes utilisés :croisement : PMX, OX, LOXmutation : transposition, transposition aléatoire, inversion sélection : sélection roulette, sélection déterministe

Des explications sur ce que j'ai fait :

Le problème consiste à rechercher les chemins optimaux en mettant en valeur les séquences efficaces relevables dans les chemins. Par une trajectoire aléatoire pondérée par les efficiences des chemins, ceux-ci sont à la suite sélectionnés, supprimés, mutés, et croisés.L'un des problèmes a été de fixer les probabilités de mutation et de croisement, j'ai pris le partie de tester deux cas différents :
-celui où ces probabilités sont constantes
-celui où ces probabilités sont variables en fonction de l'évolution des résultats. Pour en tenir compte, on pose comme probabilité :P= 1-Math.exp(-(Math.abs((Lchemin(i+1)-Lchemin(i))))/ (Lchemin(i)*tho))) où tho est un paramètre régulateur (augmentant ou diminuant l'influencedu gradient) et :grad(longchemin)= (Lchemin(i)-Lchemin(i-1))/(Lchemin(i-1).
Néanmoins cette méthode présente intuitivement ces limites : elle permet d'éviter de quitter un état optimale (par mutations et croisements nuisifs) puique si cette état perdure, P est faible ; mais elle permet également de « persister dans son erreur », puisque de le même façon, P est faible.
Cependant il apparaît intuitivement que la présence de chemins très mauvais, et donc leur risque de persistence est avant tout relevable pour des nombre d'itérations faibles. Espérons qu'en effectuant de grands nombres d'itérations , seuls des chemins optimaux restent de perdurer.Pour contrer ce problème, j'ai alors introduit un nouveau terme de correction tenant compte du signe du gradient, l'équation devient :
P= 1-Math.exp(-(Math.abs((Lchemin(i+1)-Lchemin(i)))+gradregul*(Lchemin(i+1)-Lchemin(i))  )/(Lchemin(i)*tho)))
Enfin j'ai fait varier la valeur de mon gradient pour le rendre significatif, j'ai considérer non plus le gradient instantané comme au dessus, mais la valeur moyenne prise par le gradient sur les« gradsize » derniers résultats. Gradsize est ainsi un nouveau paramêtre que l'on peut faire évoluer.       
Ainsi la fonction factory de la classe main prend les paramètres suivants : 

-mutation-croisement-sélection-paramètre tho pour la probabilité de croisement (thocrois)-paramètre tho pour la probabilité de mutation (thomut)-nombre d'itérations-infgrad (utilise si je veux arrêter les itérations à partir du stade où le gradient passe sous cette valeur (prendre 0 annule cette condition).-gradsize (le nombre de gradients dont je fais la moyenne pour obtenir celui que je veux utliser) 
-gradregul (utilisé dans ma probabilité aussi), cf plus haut.
Un rapide tour d'horizon des classes : 

-croisement code les types de croisement. 
-mutation code les types de mutation.-annexe : outils-matricedistance récupère une matrice de distances de 250 villes du département de l'Yonne. -shuffle permet de coder des permutations aléatoires.-tris code le trie fusion que j'ai utilisé dans sélection. (en O(ln(n)n).-Main est la classe la plus importante : elle génère une nouvelle population, regroupe les résultats des différentes étapes (sélections...) et renvoie le résultat grâce à factory sous forme d'une matrice composé de tableaux de ce type : [nbitérations, le meilleur résulat obtenu lors des itérations, l'indice de celui-ci, le dernier résultat, le temps d'éxécution].La fonction resultatspourgraphe renvoie la même chose mais en string pour l'obtention d'un graphe.-Fichier_resultat renvoie la matrice de string sur un fihcier txt à partir duquel je transforme sur Python ma matrice en matrice de float et en tire les graphes que je souhaite (je trouvais les graphes plus esthétiques sur Python).
Les paramètres globaux :
nbchemins :nombre de chemin dans la population P 

nbVilles: taille d'un chemin (en nombre de villes) 

nbchoisis : nombre de chemins choisis dans la population S 

matrice chemin : distancier choisismatricechemin =matricedistance.genermat(); permet de passer à la grande matrice que j'ai construite à partir d'un tableau trouvé sur le web (250 villes de l'Yonne)