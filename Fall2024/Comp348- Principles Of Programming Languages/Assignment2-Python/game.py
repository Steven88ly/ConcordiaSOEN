import random, os, time, sys, string
from grid import Grid, Cell

# Constants for various prompt and error messages
MENU_INPUT_PROMPT_MSG = "Select: "
MENU_INPUT_ERR_MSG = "Input Error: menu input is out of range. Please try again."
CELL_INPUT_PROMPT_MSG = "Enter cell coordinates (e.g., a0): "
CELL_INPUT_ERR_MSG = "Input error: row entry is out of range for this grid. Please try again."
CELL_INPUT_DUPLICATE_ERR_MSG = "Input error: row entry is already chosen. Please try again."

# Display the game title card
def print_title_card():
    print("------------------------")
    print("|     BRAIN BUSTER     |")
    print("------------------------\n")

# Display menu options for the user
def print_user_options():
    print("1. Let me select two elements\n2. Uncover one element for me\n3. I give up - reveal the grid\n4. New game\n5. Exit\n")

# Print the grid (calls the Grid object's method to print)
def print_grid():
    grid_obj.print_grid()

# Main screen display function that shows the title, grid, game status, and options
def print_screen():
    print_title_card()
    print_grid()
    print_game_end_msg()
    print_user_options()

# Display the game end message based on win or cheat status
def print_game_end_msg():
    global didCheat, didWin, guess_count
    if didCheat:
        print(f"\033[31mYou cheated - Loser! Your score is 0!\033[0m\n")
    elif didWin:
        print(f"\033[32mOh Happy Day. You've Won!! Your score is: {calc_score()}\033[0m\n")

# General function to get validated user input based on a comparator function
def get_input(comparator, err_msg, prompt_msg):
    while True:
        user_input = input(prompt_msg).strip()  # Trim whitespace for empty input check
        
        if not user_input:  # Check for empty input
            print("Input cannot be empty. Please try again.")
            continue

        if not comparator(user_input):  # Use comparator to validate input
            display_error(err_msg)
            continue
        
        return user_input  # Return valid input

# Display error messages in red
def display_error(err_msg):
    print(f"\033[31m{err_msg}\033[0m")

# Uncover a single cell on the grid based on user input
def uncover():
    cell = get_input(grid_obj.is_valid_cell, CELL_INPUT_ERR_MSG, CELL_INPUT_PROMPT_MSG)
    grid_obj.set_cell_found(cell)

# Function to select two cells and check for matches
def select():
    cell_selection_1 = get_input(grid_obj.is_valid_cell, CELL_INPUT_ERR_MSG, CELL_INPUT_PROMPT_MSG)
    
    # Ensure the second selection is not the same as the first
    while True:
        cell_selection_2 = get_input(grid_obj.is_valid_cell, CELL_INPUT_ERR_MSG, CELL_INPUT_PROMPT_MSG)
        if cell_selection_2 != cell_selection_1:
            break
        display_error(CELL_INPUT_DUPLICATE_ERR_MSG)
        
    # Check each selected cell for pre-existing found pairs and set them as found
    for cell in (cell_selection_1, cell_selection_2):
        if grid_obj.has_found_pair(cell):
            grid_obj.set_cell_found(cell)

    # Check if the two selected cells match
    if grid_obj.is_matching_cells(cell_selection_1, cell_selection_2):
        grid_obj.set_cell_found(cell_selection_1)
        grid_obj.set_cell_found(cell_selection_2)
    else:
        reveal_selected_cells(cell_selection_1, cell_selection_2)

# Temporarily reveal selected cells for a brief period, then hide again
def reveal_selected_cells(cell_selection_1, cell_selection_2):
    grid_obj.toggle_cell_active_status(cell_selection_1)
    grid_obj.toggle_cell_active_status(cell_selection_2)
    
    os.system("cls")  # Clear the console screen for updated display
    print_screen()  # Reprint the screen with temporarily revealed cells
    time.sleep(2)  # Wait for 2 seconds before hiding the cells again
    
    # Hide the cells again
    grid_obj.toggle_cell_active_status(cell_selection_1)
    grid_obj.toggle_cell_active_status(cell_selection_2)

# Reveal all cells, typically called when the player gives up
def giveup():
    global didCheat 
    didCheat = True
    didWin = True
    grid_obj.reveal_all_cells()

# Start a new game by reinitializing the grid and counters
def new():
    global grid_obj, guess_count, manual_reveal_count, grid_dimensions, didCheat, didWin
    grid_obj = Grid(grid_dimensions)
    didWin = False
    didCheat = False
    guess_count = 0
    manual_reveal_count = 0

# Check if user input for menu selection is a digit and within the correct range
def is_menu_option(input_value):
    return input_value.isdigit() and 1 <= int(input_value) <= 5

# Calculate the user's score based on guesses and minimum possible guesses
def calc_score():
    global guess_count, minimum_possible_guesses
    return (minimum_possible_guesses / guess_count) * 100 if guess_count else 0

# Initialize game settings and variables
grid_dimensions = 2

print(sys.argv[1])
# Check if a command-line argument for grid size is provided and valid
if len(sys.argv) > 1 and sys.argv[1].isdigit() and int(sys.argv[1]) <= 6 and int(sys.argv[1]) % 2 == 0:
    grid_dimensions = int(sys.argv[1])

minimum_possible_guesses = grid_dimensions / 2  # Calculate based on grid size
didWin = False  # Tracks if the player has won
didCheat = False  # Tracks if the player has used the uncover feature excessively
guess_count = 0  # Counts the number of guesses the player makes
manual_reveal_count = 0  # Tracks the number of manual reveals the player uses

# Start a new game at the beginning
new()

# Main game loop
while True:
    os.system("cls")  # Clear the screen each time for updated display
    print_screen()  # Print the main screen
    
    # Get and validate user menu input
    menu_input = int(get_input(is_menu_option, MENU_INPUT_ERR_MSG, MENU_INPUT_PROMPT_MSG))
    
    # Execute actions based on menu selection
    if menu_input == 1:
        if(not didWin):
            guess_count += 1
            select()
    elif menu_input == 2:
        if(not didWin):
            guess_count += 2
            manual_reveal_count += 1
            # If manual reveals exceed limit, set cheating flag and reveal all cells
            if manual_reveal_count >= grid_dimensions * grid_dimensions - 1:
                didCheat = True
                grid_obj.reveal_all_cells()
            uncover()
    elif menu_input == 3:
        if(not didWin):
            giveup()
    elif menu_input == 4:
        new()  # Start a new game
    elif menu_input == 5:
        break  # Exit the game loop
    
    # Check if win condition is met
    didWin = grid_obj.is_win_cond_met()
