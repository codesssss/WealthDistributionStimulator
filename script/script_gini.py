import pandas as pd
import matplotlib.pyplot as plt

# 读取csv文件
df = pd.read_csv('../output/gini.csv')

# 使用迭代次数作为x轴，基尼系数作为y轴
plt.plot(df.index, df['gini'])

# 设置图表标题以及x，y轴标签
plt.title('Gini Coefficient Over Iterations')
plt.xlabel('Iteration')
plt.ylabel('Gini Coefficient')

# 显示图表
plt.show()
plt.savefig('gini.png')

