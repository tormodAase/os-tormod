from PIL import Image, ImageDraw
import math


#NOTE: You have to install Pillow to use the Image libraries.
#python -m pip install Pillow

#scaling image 'image' so that it has x width and y height. Useless, use image.resize instead.
def scaleImage(image, x, y):
    image.resize((x, y));


#Normalizes the colors using the following method:
#r = r/r+g+b
#g = g/r+g+b
#b = b/r+g+b
def normalizeColors(image):
    x,y = image.size;
    for i in range(x):
        #print(i)
        for j in range(y):
            pixel = list(image.getpixel((i,j)));
            pixel[0] = pixel[0]/255;
            pixel[1] = pixel[1]/255;
            pixel[2] = pixel[2]/255;
            newPixel = list(pixel);
            extraVal = 0;
            if (pixel[0]+pixel[1]+pixel[2] == 0):
                extraVal = 1/255;
            for rgb in range(3):
                newPixel[rgb] = newPixel[rgb]/(pixel[0]+pixel[1]+pixel[2]+extraVal);
                newPixel[rgb] = int(round(newPixel[rgb]*255));
            image.putpixel((i,j), tuple(newPixel));

#Normalizes the colors using the following method:
#r = r/AverageRValue
#g = g/AverageGValue
#b = b/AverageBValue
def grayWorld(image):
    x,y = image.size;
    rAvg,gAvg,bAvg = 0,0,0;
    for i in range(x):
        print(i)
        for j in range(y):
            pixel = list(image.getpixel((i,j)));
            rAvg = rAvg+pixel[0];
            gAvg = gAvg+pixel[1];
            bAvg = bAvg+pixel[2];
    rAvg = (rAvg/(x*y))/255;
    gAvg = (gAvg/(x*y))/255;
    bAvg = (bAvg/(x*y))/255;
    for i in range(x):
        print(i)
        for j in range(y):
            pixel = list(image.getpixel((i,j)));
            pixel[0] = pixel[0]/255;
            pixel[1] = pixel[1]/255;
            pixel[2] = pixel[2]/255;
            newPixel = list(pixel);
            newPixel[0] = newPixel[0]/rAvg;
            newPixel[1] = newPixel[1]/gAvg;
            newPixel[2] = newPixel[2]/bAvg;
            for rgb in range(3):
                newPixel[rgb] = int(round(newPixel[rgb]*255));
            image.putpixel((i,j), tuple(newPixel));


            
im = Image.open("Tormod3.jpg"); #Use path to your image
im = im.resize((1000, 700)); #I tried my code on hugeass picture files, scaled them down to make it faster
im.show();
normalizeColors(im);
im.show();
grayWorld(im)
im.show();
            
