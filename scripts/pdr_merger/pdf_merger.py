import PyPDF2
import os
import shlex

# TODO Need to test the app
# TODO download it to the downloads directory in the operating system

def merge_pdfs(pdf_list, output_path):
    pdf_merger = PyPDF2.PdfFileMerger()
    
    for pdf in pdf_list:
        if os.path.exists(pdf):
            pdf_merger.append(pdf)
        else:
            print(f"File not found: {pdf}")
            return
    
    with open(output_path, 'wb') as output_pdf:
        pdf_merger.write(output_pdf)
    
    print(f"Merged PDF saved as {output_path}")

if __name__ == "__main__":
    # Prompt the user for PDF file paths
    print("Enter the paths of the PDF files to merge, separated by spaces:")
    input_string = input()
    
    # Split input string considering possible spaces in file paths
    # idk if this will work
    pdf_files = shlex.split(input_string)
    
    # Normalize paths to handle Windows backslashes and spaces
    pdf_files = [os.path.normpath(pdf) for pdf in pdf_files]
    
    # Output path for the merged PDF
    output_pdf_path = os.path.normpath(os.path.join(os.path.expanduser("~"), "Downloads", "merged_output.pdf"))
    
    # Check if all files exist
    missing_files = [pdf for pdf in pdf_files if not os.path.exists(pdf)]
    if missing_files:
        print(f"The following files are missing: {missing_files}")
    else:
        merge_pdfs(pdf_files, output_pdf_path)
