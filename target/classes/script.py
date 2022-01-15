import pandas as pd
from sklearn.feature_selection import VarianceThreshold


def select_by_variance_threshold(k, model, col):
    for i in range(1, 9000, 1):
        feat_selector = VarianceThreshold(threshold=i / 1000)
        selected = feat_selector.fit(model[col])
        mask = selected.variances_ > i / 1000
        if mask.sum() == k:
            return mask


def main():
    elements = []
    with open("arg.txt", "r") as file:
        for line in file:
            elements.append(line.rstrip().split(","))
    df_model = pd.DataFrame(elements)
    df_model = df_model.iloc[:, 0:50]
    df_model.columns = ['EXT1', 'EXT2', 'EXT3', 'EXT4', 'EXT5', 'EXT6', 'EXT7', 'EXT8', 'EXT9', 'EXT10',
                        'EST1', 'EST2', 'EST3', 'EST4', 'EST5', 'EST6', 'EST7', 'EST8', 'EST9', 'EST10',
                        'AGR1', 'AGR2', 'AGR3', 'AGR4', 'AGR5', 'AGR6', 'AGR7', 'AGR8', 'AGR9', 'AGR10',
                        'CSN1', 'CSN2', 'CSN3', 'CSN4', 'CSN5', 'CSN6', 'CSN7', 'CSN8', 'CSN9', 'CSN10',
                        'OPN1', 'OPN2', 'OPN3', 'OPN4', 'OPN5', 'OPN6', 'OPN7', 'OPN8', 'OPN9', 'OPN10']
    col_list = list(df_model)
    ext = col_list[0:10]
    est = col_list[10:20]
    agr = col_list[20:30]
    csn = col_list[30:40]
    opn = col_list[40:50]
    ext_df = df_model[ext]
    est_df = df_model[est]
    agr_df = df_model[agr]
    csn_df = df_model[csn]
    opn_df = df_model[opn]
    # Ext
    ext_df_selected = ext_df.loc[:, select_by_variance_threshold(5, df_model, ext)]
    # Est
    est_df_selected = est_df.loc[:, select_by_variance_threshold(5, df_model, est)]
    # Agr
    agr_df_selected = agr_df.loc[:, select_by_variance_threshold(5, df_model, agr)]
    # Csn
    csn_df_selected = csn_df.loc[:, select_by_variance_threshold(5, df_model, csn)]
    # Opn
    opn_df_selected = opn_df.loc[:, select_by_variance_threshold(5, df_model, opn)]
    print(list(ext_df_selected.columns))
    print(list(est_df_selected.columns))
    print(list(agr_df_selected.columns))
    print(list(csn_df_selected.columns))
    print(list(opn_df_selected.columns))


if __name__ == "__main__":
    main()
