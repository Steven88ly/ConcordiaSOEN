import random

class Cell:
    def __init__(self, key, value):
        self.key = key
        self.value = value
        self.is_active = False
        self.is_found= False

    def __str__(self):
        return f"Value: {self.value}"
    

class Grid:
    
    def __init__(self, dimensions):
        self.dimensions = dimensions
        self.initialize_columns()
        self.initialize_cells()
        self.found_values = list()
        
        
    def initialize_columns(self):
        self.column_list = list()
        for i in range(self.dimensions):
            self.column_list.append(chr(ord('a')+i))
        

    def initialize_cells(self):
        # Step 1: Generate all unique keys
        all_keys = [letter + str(number) for letter in self.column_list for number in range(self.dimensions)]

        # Step 2: Shuffle the keys to randomize
        random.shuffle(all_keys)
        
        self.cells_dict = {}

        # Step 3: Assign each number to two unique cells
        for i in range(int((self.dimensions * self.dimensions)/2)):
            # Assign the same `i` to two different cells
            key1 = all_keys.pop()  # Get a random cell key
            key2 = all_keys.pop()  # Get another random cell key
            self.cells_dict[key1] = Cell(key1, i)
            self.cells_dict[key2] = Cell(key2, i)
                
    def print_revealed_cell(self,value):
        return f"\033[33m{value}\033[0m"

    
    def print_found_cell(self,value):
        return f"\033[32m{value}\033[0m"    
                
    def print_grid(self):
        print("     [" + "]   [".join(element.upper() for element in self.column_list) + "]\n")
      
        for i in range(self.dimensions):
            row = f"[{i}]  "
            
            for column in self.column_list:
                cell = self.cells_dict[column+str(i)]
                row += (
                        "X" if not cell.is_active and not cell.is_found 
                        else self.print_revealed_cell(cell.value) if not cell.is_found 
                        else self.print_found_cell(cell.value)
                        )
                row += "     "
                

            print(row+"\n")
            
    def is_matching_cells(self,cell_selection_1, cell_selection_2):
        return self.cells_dict[cell_selection_1].value == self.cells_dict[cell_selection_2].value
        
    def has_found_pair(self,cell_selection):
        return self.cells_dict[cell_selection].value in self.found_values

    def is_valid_cell(self,key):
        return key in self.cells_dict and not self.cells_dict[key].is_found
    
    def toggle_cell_active_status(self,key):
        self.cells_dict[key].is_active = not self.cells_dict[key].is_active
        
    def set_cell_found(self,cell_selection):
        self.cells_dict[cell_selection].is_found = True
        self.found_values.append(self.cells_dict[cell_selection].value)
        
    def reveal_all_cells(self):
        for value in self.cells_dict.keys():
            self.set_cell_found(value)
            
    def is_win_cond_met(self):
        return len(self.found_values) >= self.dimensions * self.dimensions -2
                 
                    
              
