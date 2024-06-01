# Have a PDF file in the project folder to read it 
import pyttsx3

from PyPDF2 import PdfReader

# book is the name of the pdf file to read (only works for one page)
reader = PdfReader("book.pdf")
number_of_pages = len(reader.pages)
page = reader.pages[0]
text = page.extract_text()

print(text)

speaker = pyttsx3.init()

speaker.save_to_file(text, 'book_audio.mp3')
speaker.runAndWait()

speaker.stop() 