# breakout-game
Breakout game implementation in Java

# Zadanie:
Napisz grę zbliżoną do Breakout.

Krok 1.

W klasie GameCanvas napisz metody prywatne shouldBallBounceHorizontally(), shouldBallBounceVertically() oraz shouldBallBounceFromPaddle(), wszystkie zwracające wartość logiczną, a sprawdzające warunek opisany swoim identyfikatorem.

W klasie Ball napisz metody publiczne  bounceHorizontally() i bounceVertically() modyfikujące wektor ruchu. Wywołaj te metody, jeżeli zostaną spełnione warunki. Odbicie od platformy potraktuj jako poziome.

Krok 2.

Napisz klasę Brick, która dziedziczy po GraphicsItem i reprezentuje jedną z cegieł. W klasie tej utwórz statyczne, prywatne pola gridRows i gridCols. Wyobrażona siatka ma podzielić cały dostępny ekran na równe prostokąty. Cegła będzie mogła znaleźć się w pojedynczej pozycji siatki. Napisz metodę ustawiającą te pola.

Napisz konstruktor, w którym przekazane zostaną całkowite pozycje x i y na siatce oraz kolor. Zaimplementuj metodę draw, tak aby rysunek symbolizował trójwymiarowość cegły.

W klasie GameCanvas stwórz listę obiektów Brick oraz metodę loadLevel, która ustawi siatkę na 20 wierszy i 10 kolumn, a wiersze od 2 do 7 zapełni cegłami, każdy wiersz w innym kolorze.

Krok 3.

W klasie Ball napisz publiczne metody zwracające jej skrajne punkty: górny, dolny, lewy i prawy.

W klasie Brick zdefiniuj publiczny typ wyliczeniowy CrushType {NoCrush, HorizontalCrush, VerticalCrush}. Napisz metodę, która przyjmie cztery punkty zwrócone przez Ball i na ich podstawie stwierdzi, czy powinna ona rozbić cegłę, a jeżeli tak, to w jaki sposób. Powiąż odpowiedź z tej funkcji z odpowiednim sposobem odbicia piłki.

Krok 4.

W klasie Ball napisz metodę bounceFromPaddle, która przyjmie zmiennoprzecinkowy parametr. Parametr ten powinien być proporcjonalny do odległości pozycji uderzenia piłki od środka platformy i powinien posłużyć do obliczenia zmodyfikowanego wektora ruchu tak, aby uderzenie bliżej końców powodowało ruch pod większym kątem.
