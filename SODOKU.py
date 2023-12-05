import sys

solution_found = False

def reject(board):
    # Comprueba si hay algún número repetido en una fila, columna o submatriz 3x3
    for i in range(9):
        row_nums = [num for num in board[i] if num != 0]
        #El len sirve para saber si hay numeros repetidos en la fila
        if len(set(row_nums)) != len(row_nums):
            return True
        #lo mismo que el anterior pero para las columnas
        col_nums = [board[j][i] for j in range(9) if board[j][i] != 0]
        if len(set(col_nums)) != len(col_nums):
            return True
#El inicial_row y el inicial_col sirven para saber si hay numeros repetidos en la submatriz
    for inicial_row in range(0, 9, 3):
        for inicial_col in range(0, 9, 3):
            subgrid_nums = []
            for i in range(3):
                for j in range(3):
                    num = board[inicial_row + i][inicial_col + j]
                    if num != 0:
                        subgrid_nums.append(num)
            if len(set(subgrid_nums)) != len(subgrid_nums):
                return True

    return False


def accept(board):
    # Comprueba si el Sudoku está completo (no hay ceros en el tablero)
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return False
    return True


def output(board):
    global solution_found
    solution_found = True
    print("\n")
    # Imprime la solución del Sudoku
    for row in board:
        #El join sirve para unir los numeros de la fila
        print(" ".join(str(num) for num in row))


def first(board):
    # Encuentra la primera posición vacía en el tablero
    for i in range(9):
        for j in range(9):
            if board[i][j] == 0:
                return (i, j)
    return None


def next(board, pos):
    # Encuentra la siguiente posición vacía en el tablero después de una posición dada
    row, col = pos[0], pos[1]
    for i in range(row, 9):
        start_col = col + 1 if i == row else 0
        for j in range(start_col, 9):
            if board[i][j] == 0:
                return (i, j)

    return None

def backtracking(board, pos):
    if reject(board):
        return
        
    if accept(board):
        output(board)
        sys.exit(0)
    pos=first(board)
    row, col = pos[0], pos[1]
    num = 1
    while pos is not None and num < 10:
        board[row][col] = num
        backtracking(board, next(board, pos))
        board[row][col] = 0
        num += 1

    return False

# Leer el tablero del Sudoku desde el teclado
#print("Ingresa el Sudoku (fila por fila):")
board = []
for _ in range(9):
    row = list(map(int, input().split()))
    board.append(row)

backtracking(board, 0)

if not solution_found:
    print("No hay solución")