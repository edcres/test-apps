from pytube import YouTube
import os

def download_youtube_video():
    try:
        # Get URL input from the user
        video_url = input("Enter the YouTube video URL: ")

        # Create a YouTube object
        yt = YouTube(video_url)

        # Get all streams and filter for mp4 files
        mp4_streams = yt.streams.filter(file_extension='mp4').all()

        # Get the video with the highest resolution
        d_video = mp4_streams[-1]

        # Define the download path
        download_path = os.path.join(os.path.expanduser("~"), "Downloads")

        # Download the video
        d_video.download(output_path=download_path)
        print(f"Downloading '{yt.title}'...")
        print(f"Download completed! Video saved to '{download_path}'")
    except Exception as e:
        print(f"An error occurred: {e}")

if __name__ == "__main__":
    download_youtube_video()



# from pytube import YouTube
# import os

# def download_youtube_video():
#     try:
#         # Get URL input from the user
#         video_url = input("Enter the YouTube video URL: ")
        
#         # Create a YouTube object
#         yt = YouTube(video_url)
        
#         # Get the highest resolution stream available
#         stream = yt.streams.get_highest_resolution()
        
#         # Define the download path
#         download_path = os.path.join(os.path.expanduser("~"), "Downloads")
        
#         # Download the video
#         print(f"Downloading '{yt.title}'...")
#         stream.download(output_path=download_path)
#         print(f"Download completed! Video saved to '{download_path}'")
#     except Exception as e:
#         print(f"An error occurred: {e}")

# if __name__ == "__main__":
#     download_youtube_video()



# from pytube import YouTube
# from sys import argv

# url = input("Enter the YouTube URL: ")

# yt = YouTube(url)

# print("Title: ", yt.title)

# print("View: ", yt.views)

# yd = yt.streams.get_highest_resolution()

# print('here')

# yd.download('\\Users\\cresp\\OneDrive\\Desktop\\yt_vids')
