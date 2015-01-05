A l'attention de P-E
====================
J'ai mis dans ce README un résumé de ce que j'ai fait pour le projet Android et pour le projet Rails, afin de te simplifier un peu la correction (et de m'y retrouver moi-même).


Projet Android
--------------

* ✔ récupération de donnée sur une interface REST (NB: étant donné que l'API pour BarTendr est en cours de discussion, et qu'elle n'est donc pas entièrement implémentée, en attendant je ne récupère pas les JSON sur le serveur local mais [ici](https://bitbucket.org/v-marquet/v-marquet.bitbucket.org))
* ✔ .apk envoyé par mail
* ✔ code compilable sur [GitHub](https://github.com/vmarquet/bartendr-client-android) (j'utilise Android Studio)

### Design patterns

* ✔ Toast
* ✔ AsyncTask
* ✔ Activity
* ✘ ActionBar
* ✔ ListView
* ✔ JSON (récupération et parsing)
* ✘ Fragment
* ✘ IntentService
* ✘ BroadcastReciever
* ✘ ViewPager


Projet Rails
------------

* ✔ une interface REST accessible en html et json. Exemple, pour la table "articles":
    * HTML: http://0.0.0.0:3000/articles.html
    * JSON: http://0.0.0.0:3000/articles.json
* ✔ plus de 2 model associé de façon différentes. Pour le moment, il y a trois tables reliées entre elles: `articles`, la liste des articles en vente, `orders`, les commandes effectuées, et `items`, ce qui est commandé dans chaque commande. Exemple (cf [ici](https://github.com/vmarquet/bartendr-server/tree/master/server-ror/app/models):
    * une commande a plusieurs items: `has_many :items` dans [orders.rb](https://github.com/vmarquet/bartendr-server/blob/master/server-ror/app/models/order.rb)
    * un item fait partie d'une commande: `belongs_to :order` dans [items.rb](https://github.com/vmarquet/bartendr-server/blob/master/server-ror/app/models/item.rb)
* ✔ instructions pour mettre en place le serveur:
```
git clone https://github.com/vmarquet/bartendr-server.git
cd bartendr-server/server-ror/
rake db:migrate
rails s
```
* ✔ l'application marche localement
* ✔ une base de donnée capable d'être migrate et rollback
* ✔ le code disponible sur [github](https://github.com/vmarquet/bartendr-server/tree/master/server-ror)


![BarTendr logo](https://drive.google.com/uc?export=view&id=0B31-CIvNW1LdeU0xSnBYNEJWeDQ)
