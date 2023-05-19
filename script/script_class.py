import pandas as pd
import matplotlib.pyplot as plt

# 读取CSV文件
df = pd.read_csv('../output/WealthClassNum.csv')
df['Iteration rounds'] = range(1, len(df) + 1)
# 绘制折线图
plt.figure(figsize=(10,6))

plt.plot(df.index, df['Poor'], label='Poor')
plt.plot(df.index, df['Middle'], label='Middle')
plt.plot(df.index, df['Rich'], label='Rich')

# 添加图例
plt.legend()

# 设置y轴标签
plt.ylabel('Number of people')

# 设置x轴标签
plt.xlabel('Iteration rounds')

# 设置图表标题
plt.title('Graph of order variation with iteration rounds')

# 显示图表
plt.show()

plt.savefig('class.png')

