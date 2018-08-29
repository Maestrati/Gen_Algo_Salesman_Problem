# Gen_Algo_Salesman_Problem

Les diff�rentes m�thodes utilis�s :croisement : PMX, OX, LOXmutation : transposition, transposition al�atoire, inversion s�lection : s�lection roulette, s�lection d�terministe

Des explications sur ce que j'ai fait :

Le probl�me consiste � rechercher les chemins optimaux en mettant en valeur les s�quences efficaces relevables dans les chemins. Par une trajectoire al�atoire pond�r�e par les efficiences des chemins, ceux-ci sont � la suite s�lectionn�s, supprim�s, mut�s, et crois�s.L'un des probl�mes a �t� de fixer les probabilit�s de mutation et de croisement, j'ai pris le partie de tester deux cas diff�rents :
-celui o� ces probabilit�s sont constantes
-celui o� ces probabilit�s sont variables en fonction de l'�volution des r�sultats. Pour en tenir compte, on pose comme probabilit� :P= 1-Math.exp(-(Math.abs((Lchemin(i+1)-Lchemin(i))))/ (Lchemin(i)*tho))) o� tho est un param�tre r�gulateur (augmentant ou diminuant l'influencedu gradient) et :grad(longchemin)= (Lchemin(i)-Lchemin(i-1))/(Lchemin(i-1).
N�anmoins cette m�thode pr�sente intuitivement ces limites : elle permet d'�viter de quitter un �tat optimale (par mutations et croisements nuisifs) puique si cette �tat perdure, P est faible ; mais elle permet �galement de � persister dans son erreur �, puisque de le m�me fa�on, P est faible.
Cependant il appara�t intuitivement que la pr�sence de chemins tr�s mauvais, et donc leur risque de persistence est avant tout relevable pour des nombre d'it�rations faibles. Esp�rons qu'en effectuant de grands nombres d'it�rations , seuls des chemins optimaux restent de perdurer.Pour contrer ce probl�me, j'ai alors introduit un nouveau terme de correction tenant compte du signe du gradient, l'�quation devient :
P= 1-Math.exp(-(Math.abs((Lchemin(i+1)-Lchemin(i)))+gradregul*(Lchemin(i+1)-Lchemin(i))  )/(Lchemin(i)*tho)))
Enfin j'ai fait varier la valeur de mon gradient pour le rendre significatif, j'ai consid�rer non plus le gradient instantan� comme au dessus, mais la valeur moyenne prise par le gradient sur les� gradsize � derniers r�sultats. Gradsize est ainsi un nouveau param�tre que l'on peut faire �voluer.       
Ainsi la fonction factory de la classe main prend les param�tres suivants : 

-mutation-croisement-s�lection-param�tre tho pour la probabilit� de croisement (thocrois)-param�tre tho pour la probabilit� de mutation (thomut)-nombre d'it�rations-infgrad (utilise si je veux arr�ter les it�rations � partir du stade o� le gradient passe sous cette valeur (prendre 0 annule cette condition).-gradsize (le nombre de gradients dont je fais la moyenne pour obtenir celui que je veux utliser) 
-gradregul (utilis� dans ma probabilit� aussi), cf plus haut.
Un rapide tour d'horizon des classes : 

-croisement code les types de croisement. 
-mutation code les types de mutation.-annexe : outils-matricedistance r�cup�re une matrice de distances de 250 villes du d�partement de l'Yonne. -shuffle permet de coder des permutations al�atoires.-tris code le trie fusion que j'ai utilis� dans s�lection. (en O(ln(n)n).-Main est la classe la plus importante : elle g�n�re une nouvelle population, regroupe les r�sultats des diff�rentes �tapes (s�lections...) et renvoie le r�sultat gr�ce � factory sous forme d'une matrice compos� de tableaux de ce type : [nbit�rations, le meilleur r�sulat obtenu lors des it�rations, l'indice de celui-ci, le dernier r�sultat, le temps d'�x�cution].La fonction resultatspourgraphe renvoie la m�me chose mais en string pour l'obtention d'un graphe.-Fichier_resultat renvoie la matrice de string sur un fihcier txt � partir duquel je transforme sur Python ma matrice en matrice de float et en tire les graphes que je souhaite (je trouvais les graphes plus esth�tiques sur Python).
Les param�tres globaux :
nbchemins :nombre de chemin dans la population P 

nbVilles: taille d'un chemin (en nombre de villes) 

nbchoisis : nombre de chemins choisis dans la population S 

matrice chemin : distancier choisismatricechemin =matricedistance.genermat(); permet de passer � la grande matrice que j'ai construite � partir d'un tableau trouv� sur le web (250 villes de l'Yonne)