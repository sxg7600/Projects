import tkinter as tk
import time
import math

def update_time():
    current_time = time.localtime()
    draw_clock(current_time)
    update_digital_time(current_time)
    root.after(1000, update_time)  # Update every second

def draw_clock(current_time):
    canvas.delete("all")

    # Get the center of the canvas
    center_x = canvas.winfo_reqwidth() / 2
    center_y = canvas.winfo_reqheight() / 2

    # Calculate clock face radius and draw it
    clock_radius = min(center_x, center_y) * 0.9
    canvas.create_oval(center_x - clock_radius, center_y - clock_radius, center_x + clock_radius, center_y + clock_radius)

    # Draw clock numbers
    for i in range(1, 13):
        angle = math.radians(i * 30)
        num_x = center_x + (clock_radius - 30) * math.cos(angle)
        num_y = center_y - (clock_radius - 30) * math.sin(angle)
        canvas.create_text(num_x, num_y, text=str(i), font=("Helvetica", 16, "bold"))

    # Draw clock hands
    draw_clock_hand(center_x, center_y, clock_radius * 0.6, current_time.tm_hour % 12 * 30 + current_time.tm_min / 2)
    draw_clock_hand(center_x, center_y, clock_radius * 0.8, current_time.tm_min * 6)
    draw_clock_hand(center_x, center_y, clock_radius * 0.8, current_time.tm_sec * 6, width=2, color='red')

def draw_clock_hand(center_x, center_y, length, angle, width=4, color='black'):
    angle_rad = math.radians(-angle + 90)
    end_x = center_x + length * math.cos(angle_rad)
    end_y = center_y - length * math.sin(angle_rad)
    canvas.create_line(center_x, center_y, end_x, end_y, width=width, fill=color)

def update_digital_time(current_time):
    digital_time = time.strftime("%I:%M:%S %p", current_time)
    digital_label.config(text=digital_time)

root = tk.Tk()
root.title("Analog and Digital Wall Clock")

# Get the screen width and height
screen_width = root.winfo_screenwidth()
screen_height = root.winfo_screenheight()

# Set the main frame size to match the screen size
root.geometry(f"{screen_width}x{screen_height}")

canvas = tk.Canvas(root, bg='white')
canvas.pack(fill=tk.BOTH, expand=True)

digital_label = tk.Label(root, text="", font=("Helvetica", 24))
digital_label.pack(pady=10)

update_time()  # Start updating the time

root.mainloop()
