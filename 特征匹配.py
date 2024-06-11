import cv2
import numpy as np

def cv_show(name,img):
    cv2.imshow(name,img)
    cv2.waitKey(0)
    cv2.destroyAllWindows()

# 加载图片
img = cv2.imread('D:/openCV/image_Test/targetImages/0611O.png')
# 加载模版
temp = cv2.imread('D:/openCV/image_Test/targetImages/0611T3.png')

# 转灰度图
gray_img = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
gray_temp = cv2.cvtColor(temp,cv2.COLOR_BGR2GRAY)

# cv_show("gray_img",gray_img)
# cv_show("gray_temp",gray_temp)

# 生成特征点
sift = cv2.xfeatures2d.SIFT_create()
kp1,des1 = sift.detectAndCompute(gray_img,None)
kp2,des2 = sift.detectAndCompute(gray_temp,None)

# 匹配两张图片的特征
bf = cv2.BFMatcher(crossCheck=True)

# 进行匹配
matches = bf.match(des1,des2)
matches = sorted(matches,key=lambda x:x.distance)

img3 = cv2.drawMatches(img,kp1,temp,kp2,matches[:10],None,flags=2)
cv_show("img3",img3)

# 提取前10个最佳匹配的坐标
for match in matches[:10]:
    # 获取查询图像中的关键点坐标
    query_pt = kp1[match.queryIdx].pt
    # 获取训练图像（模板）中的关键点坐标
    train_pt = kp2[match.trainIdx].pt

    # 打印坐标（或者将它们存储到某个列表或数组中）
    print(f"Query Point: {query_pt}, Train Point: {train_pt}")



# 获取坐标点之后返回坐标
