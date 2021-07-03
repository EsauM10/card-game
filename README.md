# Card Game
Esse projeto utiliza o padrão Strategy para facilitar a implementação de jogos de cartas
## Estrutura do projeto

## Solitaire
No jogo Solitaire o objetivo é completar as 4 fundações (pilhas vazias) em ordem ascendente com cartas do mesmo naipe.

Pilhas:
- 1 Estoque
- 1 Descarte
- 4 Fundações
- 7 Fileiras

Regras:

- A pilha de Estoque contém 24 cartas aleatórias viradas para baixo
- A pilha de Descarte e as Fundações são inicialmente vazias
- As Fileiras são inicializadas variando o número de cartas de 1 até 7, com apenas a carta do topo virada para cima
- Cada Fileira deve ser construída em ordem descendente com cartas de cores alternadas

Movimentos permitidos:

- Estoque -> Descarte
- Descarte -> Fundação
- Descarte -> Fileira
- Fundação -> Fileira
- Fileira -> Fundação
- Fileira -> Fileira
![solitaire](https://user-images.githubusercontent.com/33939999/124337830-fa7e3400-db7a-11eb-8a65-4b0995d303cb.png)

## BigBertha
Essa implementação utiliza 2 baralhos, e o objetivo é completar todas as fundações.

Pilhas:
- 1 Estoque
- 9 Fundações (a última aceita apenas Reis)
- 15 Fileiras

Regras:

- O Estoque é inicializado com 14 cartas aleatórias viradas para cima (é possível mover qualquer carta)
- As Fundações começam inicialmente vazias
- As Fileiras são inicializadas com 6 cartas viradas para cima

Movimentos permitidos:

- Estoque -> Fundação
- Estoque -> Fileira
- Fileira -> Fundação
- Fileira -> Fileira
![bigbertha](https://user-images.githubusercontent.com/33939999/124338260-2d292c00-db7d-11eb-952c-4b41822c8f3e.png)
